package org.message.consumer.util;

import lombok.experimental.UtilityClass;
import org.message.model.util.BrokerType;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@UtilityClass
public class TestProgressUtil {
    private static final AtomicInteger TOTAL_MESSAGES_OBTAINED = new AtomicInteger(0);
    private static final ConcurrentHashMap<BrokerType, Integer> BROKER_TOTAL_MESSAGES_OBTAINED = new ConcurrentHashMap<>();

    public static synchronized int getTotalMessagesObtained() {
        return TOTAL_MESSAGES_OBTAINED.get();
    }

    public static synchronized void setTotalMessagesObtained(int value) {
        TOTAL_MESSAGES_OBTAINED.set(value);
    }

    public static synchronized void incrementTotalMessagesObtained() {
        TOTAL_MESSAGES_OBTAINED.incrementAndGet();
    }

    public static synchronized int getBrokerTotalMessagesObtained(BrokerType brokerType) {
        return BROKER_TOTAL_MESSAGES_OBTAINED.getOrDefault(brokerType, 0);
    }

    public static synchronized void setBrokerTotalMessagesObtainedAllBrokers(int value) {
        for (BrokerType brokerType : BrokerType.values()) {
            BROKER_TOTAL_MESSAGES_OBTAINED.put(brokerType, value);
        }
    }

    public static synchronized void incrementBrokerTotalMessagesObtained(BrokerType brokerType) {
        int currentValue = BROKER_TOTAL_MESSAGES_OBTAINED.getOrDefault(brokerType, 0);
        currentValue++;
        BROKER_TOTAL_MESSAGES_OBTAINED.put(brokerType, currentValue);
    }
}
