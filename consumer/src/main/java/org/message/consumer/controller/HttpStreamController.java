package org.message.consumer.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.message.consumer.exception.SseSendMessageException;
import org.message.consumer.util.MessageType;
import org.message.model.DebugInfo;
import org.message.model.Report;
import org.message.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.message.consumer.util.StreamMessageUtil.consumeMessage;
import static org.message.consumer.util.StreamMessageUtil.getCountMessages;

@Slf4j
@RestController
@RequestMapping("/api/v1/data-stream")
public class HttpStreamController {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @PostConstruct
    public void init() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            executor.shutdown();
            try {
                boolean isTerminated = executor.awaitTermination(1, TimeUnit.SECONDS);
                log.debug("[HttpStreamController:PostConstruct] Executor termination status: {}", isTerminated);
            } catch (InterruptedException e) {
                log.error(e.toString());
                Thread.currentThread().interrupt();
            }
        }));
    }

    @GetMapping(value = "/debug-info")
    public SseEmitter streamDebugInfoMessages() {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        configureSseEmitter(sseEmitter);

        executor.execute(() -> {
            log.info("Start Http stream");
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
            } while (debugInfo.getTestStatusPercentage() < 100);
            sseEmitter.complete();
        });
        log.info("Debug info stream controller exits");
        return sseEmitter;
    }

    @GetMapping(value = "/user")
    public SseEmitter streamUserMessages() {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        configureSseEmitter(sseEmitter);

        executor.execute(() -> {
            while (getCountMessages(MessageType.USER) > 0) {
                User user = (User) consumeMessage(MessageType.USER);
                try {
                    sseEmitter.send(user);
                } catch (IOException e) {
                    throw new SseSendMessageException(e);
                }
                log.debug("User message streamed: {}", user);
            }
            sseEmitter.complete();
        });

        log.debug("User stream controller exits");
        return sseEmitter;
    }

    @GetMapping(value = "/report")
    public SseEmitter streamReportMessages() {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        configureSseEmitter(sseEmitter);

        executor.execute(() -> {
            while (getCountMessages(MessageType.REPORT) > 0) {
                Report report = (Report) consumeMessage(MessageType.REPORT);
                try {
                    sseEmitter.send(report);
                } catch (IOException e) {
                    throw new SseSendMessageException(e);
                }
                log.debug("Report message streamed: {}", report);
            }
            sseEmitter.complete();
        });

        log.debug("Report stream controller exits");
        return sseEmitter;
    }

    private void configureSseEmitter(SseEmitter sseEmitter) {
        sseEmitter.onCompletion(() -> log.debug("SseEmitter is completed"));
        sseEmitter.onTimeout(() -> log.debug("SseEmitter is timed out"));
        sseEmitter.onError(ex -> log.debug("SseEmitter got error:", ex));
    }
}
