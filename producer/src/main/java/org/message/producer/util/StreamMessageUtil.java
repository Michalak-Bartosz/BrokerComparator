package org.message.producer.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.message.model.DebugInfo;
import org.message.model.Report;
import org.message.model.User;
import org.message.producer.exception.ConsumeStreamMessageTypeNPException;
import org.message.producer.exception.NotValidStreamMessageTypeException;

import java.util.Comparator;
import java.util.PriorityQueue;

import static org.message.producer.util.MessageType.*;

@Slf4j
@UtilityClass
public class StreamMessageUtil {
    private static final PriorityQueue<DebugInfo> DEBUG_INFO_STREAM_QUEUE = new PriorityQueue<>(Comparator.comparingInt(DebugInfo::getCountOfProducedMessages));
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
                if (getCountMessages(DEBUG_INFO) > 0) {
                    DebugInfo debugInfo = DEBUG_INFO_STREAM_QUEUE.poll();
                    log.debug("CONSUMED DEBUG INFO MESSAGE: {}", debugInfo);
                    return debugInfo;
                }
                return null;
            }
            case USER -> {
                if (getCountMessages(USER) > 0) {
                    User user = USER_STREAM_QUEUE.poll();
                    log.debug("CONSUMED USER MESSAGE: {}", user);
                    return user;
                }
                return null;
            }
            case REPORT -> {
                if (getCountMessages(REPORT) > 0) {
                    Report report = REPORT_STREAM_QUEUE.poll();
                    log.debug("CONSUMED DEBUG INFO MESSAGE: {}", report);
                    return report;
                }
                return null;
            }
            case null -> throw new ConsumeStreamMessageTypeNPException();
            default -> throw new NotValidStreamMessageTypeException();
        }
    }

    public static int getCountMessages(MessageType messageType) {
        switch (messageType) {
            case DEBUG_INFO -> {
                log.debug("COUNT DEBUG INFO MESSAGE TO CONSUME: {}", DEBUG_INFO_STREAM_QUEUE.size());
                return DEBUG_INFO_STREAM_QUEUE.size();
            }
            case USER -> {
                log.debug("COUNT USER MESSAGE TO CONSUME: {}", USER_STREAM_QUEUE.size());
                return USER_STREAM_QUEUE.size();
            }
            case REPORT -> {
                log.debug("COUNT REPORT MESSAGE TO CONSUME: {}", REPORT_STREAM_QUEUE.size());
                return REPORT_STREAM_QUEUE.size();
            }
            case null -> throw new ConsumeStreamMessageTypeNPException();
            default -> throw new NotValidStreamMessageTypeException();
        }
    }
}
