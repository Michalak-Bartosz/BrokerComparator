package org.message.producer.util;


import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

@UtilityClass
public class TestProgressUtil {

    private static final AtomicInteger TOTAL_MESSAGES_TO_SEND = new AtomicInteger(0);
    private static final AtomicInteger TOTAL_MESSAGES_OBTAINED = new AtomicInteger(0);
    private static final AtomicInteger CURRENT_BROKER_TOTAL_PRODUCED_DATA_SIZE_IN_BYTES = new AtomicInteger(0);
    private static final AtomicInteger CURRENT_BROKER_MESSAGES_TO_SEND = new AtomicInteger(0);
    private static final AtomicInteger CURRENT_BROKER_MESSAGES_OBTAINED = new AtomicInteger(0);
    private static final AtomicInteger MESSAGE_OBTAINED_IN_ATTEMPT = new AtomicInteger(0);

    public static synchronized BigDecimal getCurrentTestStatusPercentage() {
        return BigDecimal.valueOf((incrementTotalMessagesObtained() * 100L) / TOTAL_MESSAGES_TO_SEND.get());
    }

    private static synchronized int incrementTotalMessagesObtained() {
        return TOTAL_MESSAGES_OBTAINED.incrementAndGet();
    }

    public static synchronized BigDecimal getCurrentBrokerStatusPercentage() {
        return BigDecimal.valueOf((incrementCurrentBrokerMessagesObtained() * 100L) / CURRENT_BROKER_MESSAGES_TO_SEND.get());
    }

    private static synchronized int incrementCurrentBrokerMessagesObtained() {
        return CURRENT_BROKER_MESSAGES_OBTAINED.incrementAndGet();
    }

    public static synchronized int getTotalMessagesToSend() {
        return TOTAL_MESSAGES_TO_SEND.get();
    }

    public static synchronized void setTotalMessagesToSend(int value) {
        TOTAL_MESSAGES_TO_SEND.set(value);
    }

    public static synchronized int getTotalMessagesObtained() {
        return TOTAL_MESSAGES_OBTAINED.get();
    }

    public static synchronized void setTotalMessagesObtained(int value) {
        TOTAL_MESSAGES_OBTAINED.set(value);
    }


    public static synchronized void setCurrentBrokerTotalProducedDataSizeInBytes(int value) {
        CURRENT_BROKER_TOTAL_PRODUCED_DATA_SIZE_IN_BYTES.set(value);
    }

    public static synchronized int addToCurrentBrokerTotalProducedDataSizeInBytes(int value) {
        return CURRENT_BROKER_TOTAL_PRODUCED_DATA_SIZE_IN_BYTES.addAndGet(value);
    }

    public static synchronized void setCurrentBrokerMessagesToSend(int value) {
        CURRENT_BROKER_MESSAGES_TO_SEND.set(value);
    }

    public static synchronized void setCurrentBrokerMessagesObtained(int value) {
        CURRENT_BROKER_MESSAGES_OBTAINED.set(value);
    }

    public static synchronized void setMessageObtainedInAttempt(int value) {
        MESSAGE_OBTAINED_IN_ATTEMPT.set(value);
    }

    public static synchronized int incrementMessageObtainedInAttempt() {
        return MESSAGE_OBTAINED_IN_ATTEMPT.incrementAndGet();
    }
}
