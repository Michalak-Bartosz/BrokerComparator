package org.message.consumer.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.message.consumer.exception.ConsumeStreamMessageTypeNPException;
import org.message.consumer.exception.NotValidStreamMessageTypeException;
import org.message.model.DebugInfo;
import org.message.model.Report;
import org.message.model.User;

@Slf4j
@UtilityClass
public class StreamMessageUtil {
    private static final StreamQueue<DebugInfo> DEBUG_INFO_STREAM_QUEUE = new StreamQueue<>();
    private static final StreamQueue<User> USER_STREAM_QUEUE = new StreamQueue<>();
    private static final StreamQueue<Report> REPORT_STREAM_QUEUE = new StreamQueue<>();

    public void addMessage(DebugInfo newMessage) {
        log.debug("UPDATE QUEUE WITH DEBUG INFO VALUE: {}", newMessage.toString());
        DEBUG_INFO_STREAM_QUEUE.add(newMessage);
    }

    public void addMessage(User newMessage) {
        log.debug("UPDATE QUEUE WITH USER VALUE: {}", newMessage.toString());
        USER_STREAM_QUEUE.add(newMessage);
    }

    public void addMessage(Report newMessage) {
        log.debug("UPDATE QUEUE WITH REPORT VALUE: {}", newMessage.toString());
        REPORT_STREAM_QUEUE.add(newMessage);
    }

    public static Object consumeMessage(MessageType messageType) {
        switch (messageType) {
            case DEBUG_INFO -> {
                DebugInfo debugInfo = DEBUG_INFO_STREAM_QUEUE.poll();
                log.debug("CONSUMED DEBUG INFO MESSAGE: {}", debugInfo);
                return debugInfo;
            }
            case USER -> {
                User user = USER_STREAM_QUEUE.poll();
                log.debug("CONSUMED USER MESSAGE: {}", user);
                return user;
            }
            case REPORT -> {
                Report report = REPORT_STREAM_QUEUE.poll();
                log.debug("CONSUMED DEBUG INFO MESSAGE: {}", report);
                return report;
            }
            case null -> throw new ConsumeStreamMessageTypeNPException();
            default -> throw new NotValidStreamMessageTypeException();
        }
    }

    public static int getCountMessages() {
        log.debug("COUNT MESSAGE TO CONSUME: {}", DEBUG_INFO_STREAM_QUEUE.size());
        return DEBUG_INFO_STREAM_QUEUE.size();
    }
}
