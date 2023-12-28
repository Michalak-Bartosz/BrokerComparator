package org.message.producer.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.message.model.DebugInfo;
import org.message.producer.exception.HttpStreamWaitException;
import org.message.producer.exception.SseSendMessageException;
import org.message.producer.util.MessageType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.message.producer.config.ApiConstants.REQUEST_MAPPING_NAME;
import static org.message.producer.util.StreamMessageUtil.consumeMessage;

@Slf4j
@RestController
@RequestMapping(REQUEST_MAPPING_NAME + "/data-stream")
public class HttpStreamController {

    public static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
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
            log.info("Start Http stream");
            startHttpStream();
            DebugInfo debugInfo;
            do {
                debugInfo = (DebugInfo) consumeMessage(MessageType.DEBUG_INFO);
                while (debugInfo == null) {
                    debugInfo = (DebugInfo) consumeMessage(MessageType.DEBUG_INFO);
                }
                try {
                    sseEmitter.send(debugInfo);
                } catch (IOException e) {
                    throw new SseSendMessageException(e);
                }
                log.info("DebugInfo message streamed: {}", debugInfo.getTestStatusPercentage());
            } while (debugInfo.getTestStatusPercentage().compareTo(BigDecimal.valueOf(100)) < 0);
            waitToFinishTest();
            log.info("Finish Http stream");
        });
        log.info("Debug info stream controller exits");
        return sseEmitter;
    }

    private static synchronized void waitToFinishTest() {
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

    private static void configureSseEmitter() {
        sseEmitter.onCompletion(() -> log.debug("SseEmitter is completed"));
        sseEmitter.onTimeout(() -> log.debug("SseEmitter is timed out"));
        sseEmitter.onError(ex -> log.debug("SseEmitter got error:", ex));
    }
}
