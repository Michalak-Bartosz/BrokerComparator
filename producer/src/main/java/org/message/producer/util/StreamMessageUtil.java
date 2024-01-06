package org.message.producer.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.message.model.DebugInfo;

import java.util.Comparator;
import java.util.PriorityQueue;

@Slf4j
@UtilityClass
public class StreamMessageUtil {
    private static final PriorityQueue<DebugInfo> DEBUG_INFO_STREAM_QUEUE = new PriorityQueue<>(Comparator.comparingInt(DebugInfo::getCountOfProducedMessages));

    public void addMessage(DebugInfo newMessage) {
        synchronized (DEBUG_INFO_STREAM_QUEUE) {
            DEBUG_INFO_STREAM_QUEUE.add(newMessage);
        }
        log.debug("UPDATE QUEUE WITH DEBUG INFO VALUE: {}", newMessage.toString());
    }

    public static Object consumeMessage() {
        if (getCountMessages() > 0) {
            DebugInfo debugInfo;
            synchronized (DEBUG_INFO_STREAM_QUEUE) {
                debugInfo = DEBUG_INFO_STREAM_QUEUE.poll();
            }
            log.debug("CONSUMED DEBUG INFO MESSAGE: {}", debugInfo);
            return debugInfo;
        }
        return null;
    }

    public static int getCountMessages() {
        log.debug("COUNT DEBUG INFO MESSAGE TO CONSUME: {}", DEBUG_INFO_STREAM_QUEUE.size());
        return DEBUG_INFO_STREAM_QUEUE.size();
    }
}
