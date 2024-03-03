package org.message.consumer.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.message.consumer.exception.HttpStreamWaitException;
import org.message.consumer.exception.SseSendMessageException;
import org.message.consumer.util.StreamMessageUtil;
import org.message.model.DebugInfo;
import org.message.model.util.BrokerType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.message.consumer.config.ApiConstants.PRODUCER_API_URL_ADDRESS;
import static org.message.consumer.config.ApiConstants.REQUEST_MAPPING_NAME;
import static org.message.consumer.util.StreamMessageUtil.consumeMessage;

@Slf4j
@RestController
@RequestMapping(REQUEST_MAPPING_NAME + "/data-stream")
public class HttpStreamController {

    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
    private static final RestClient REST_CLIENT = RestClient.create();
    private static SseEmitter sseEmitter;
    private static boolean isHttpStreamRun = false;
    private static final long WAIT_EXECUTOR_TIMEOUT_MS = 5000;

    @PostConstruct
    public void init() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            EXECUTOR.shutdown();
            try {
                boolean isTerminated = EXECUTOR.awaitTermination(60, TimeUnit.SECONDS);
                log.debug("[HttpStreamController:PostConstruct] Executor termination status: {}", isTerminated);
            } catch (InterruptedException e) {
                log.error(e.toString());
                Thread.currentThread().interrupt();
            }
        }));
    }

    @GetMapping(value = "/debug-info")
    public static SseEmitter streamDebugInfoMessages() {
        sseEmitter = new SseEmitter(Long.MAX_VALUE);
        configureSseEmitter();
        EXECUTOR.execute(() -> {
            log.info("🏁 Start Http stream");
            startHttpStream();
            DebugInfo debugInfo;
            do {
                debugInfo = (DebugInfo) consumeMessage();
                while (debugInfo == null) {
                    debugInfo = (DebugInfo) consumeMessage();
                }
                try {
                    sseEmitter.send(debugInfo);
                } catch (IOException e) {
                    throw new SseSendMessageException(e);
                }
                log.info("📈 Test status percentage: {}", debugInfo.getTestStatusPercentage());
                log.info("📉Broker status percentage: {}", debugInfo.getBrokerStatusPercentage());
                if (debugInfo.getBrokerStatusPercentage().compareTo(BigDecimal.valueOf(100L)) == 0 &&
                        debugInfo.getTestStatusPercentage().compareTo(BigDecimal.valueOf(100L)) != 0) {
                    notifyProducer(debugInfo.getBrokerType());
                }
            } while (debugInfo.getTestStatusPercentage().compareTo(BigDecimal.valueOf(100)) < 0);
            StreamMessageUtil.clearQueue();
            waitToFinishTest();
            log.info("🏁 Finish Http stream");
        });
        log.info("🔚 Debug info server sent event exits");
        return sseEmitter;
    }

    private static synchronized void waitToFinishTest() {
        log.info("🕒 Waiting to finish the test...");
        try {
            while (isHttpStreamRun) {
                HttpStreamController.class.wait(WAIT_EXECUTOR_TIMEOUT_MS);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new HttpStreamWaitException(e);
        } finally {
            sseEmitter.complete();
        }
    }

    public static synchronized void startHttpStream() {
        isHttpStreamRun = true;
    }

    public static synchronized void finishHttpStream() {
        HttpStreamController.class.notifyAll();
        isHttpStreamRun = false;
    }

    private static synchronized void notifyProducer(BrokerType brokerType) {
        REST_CLIENT.post()
                .uri(PRODUCER_API_URL_ADDRESS + "/notify")
                .body(brokerType)
                .retrieve();
        log.info("🔔 Producer notified about consumed data by broker: {}", brokerType);
    }

    private static void configureSseEmitter() {
        sseEmitter.onCompletion(() -> log.info("✅ SseEmitter is completed"));
        sseEmitter.onTimeout(() -> log.info("🕒 SseEmitter is timed out"));
        sseEmitter.onError(ex -> log.info("❌ SseEmitter got error:", ex));
    }
}
