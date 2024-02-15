package org.message.producer.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.message.model.User;
import org.message.producer.controller.HttpStreamController;
import org.message.producer.dto.FinishTestDto;
import org.message.producer.dto.TestSettingsDto;
import org.message.producer.exception.DelayBetweenTestException;
import org.message.producer.exception.HttpStreamWaitException;
import org.message.producer.exception.NotifyProducerException;
import org.message.producer.exception.UnsupportedBrokerTypeException;
import org.message.producer.kafka.KafkaProducer;
import org.message.producer.rabbitmq.RabbitMqProducer;
import org.message.producer.util.BrokerType;
import org.message.producer.util.RandomUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.message.producer.util.DataSizeUtil.getObjectSizeInBytes;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService {

    public static final AtomicInteger TOTAL_MESSAGES_TO_SEND = new AtomicInteger(0);
    public static final AtomicInteger TOTAL_MESSAGES_OBTAINED = new AtomicInteger(0);
    public static final AtomicInteger TOTAL_PRODUCED_DATA_SIZE_IN_BYTES = new AtomicInteger(0);
    public static final AtomicInteger CURRENT_BROKER_TO_SEND = new AtomicInteger(0);
    public static final AtomicInteger CURRENT_BROKER_OBTAINED = new AtomicInteger(0);
    private static final long WAIT_TO_CONSUME_TIMEOUT_MS = 5000;

    private static boolean isWaitingToConsume = false;
    private static boolean isAlreadyConsume = false;
    private static boolean isWaitingBetweenNextAttempt = false;

    public static BigDecimal getCurrentTestStatusPercentage() {
        return BigDecimal.valueOf((TOTAL_MESSAGES_OBTAINED.get() * 100L) / TOTAL_MESSAGES_TO_SEND.get());
    }

    public static BigDecimal getCurrentBrokerStatusPercentage() {
        return BigDecimal.valueOf((CURRENT_BROKER_OBTAINED.get() * 100L) / CURRENT_BROKER_TO_SEND.get());
    }

    private final KafkaProducer kafkaProducer;
    private final RabbitMqProducer rabbitMqProducer;
    private Map<BrokerType, TestRunnerFunction> testRunnerMap;

    @PostConstruct
    private void initTestRunnerMap() {
        this.testRunnerMap = Map.of(
                BrokerType.KAFKA, kafkaProducer::sendRecord,
                BrokerType.RABBITMQ, rabbitMqProducer::sendRecord);
    }

    @Transactional
    public synchronized void performTest(TestSettingsDto testSettingsDto) {
        List<User> users = prepareData(testSettingsDto);
        List<BrokerType> brokerTypes = testSettingsDto.getBrokerTypes();
        int brokerTypeSize = testSettingsDto.getBrokerTypes().size();
        TOTAL_MESSAGES_TO_SEND.set(calculateTotalMessagesToSend(testSettingsDto));
        TOTAL_MESSAGES_OBTAINED.set(0);
        TOTAL_PRODUCED_DATA_SIZE_IN_BYTES.set(0);
        waitInMilliseconds(5000L); //Wait 5s before start test to skip PC resource pick after parallel producing data
        for (int i = 0; i < brokerTypeSize; i++) {
            BrokerType brokerType = brokerTypes.get(i);
            sendRecords(brokerType,
                    testSettingsDto,
                    users);
            if (i < brokerTypeSize - 1) {
                waitToFinishConsumeBrokerData();
            }
        }
    }

    @Transactional
    public boolean finishTest(FinishTestDto finishTestDto) {
        HttpStreamController.finishHttpStream();
        boolean isFinishSuccessful = finishTestDto.getNumberOfReceivedMessagesProducer() == TOTAL_MESSAGES_OBTAINED.get();
        TOTAL_MESSAGES_OBTAINED.set(0);
        TOTAL_PRODUCED_DATA_SIZE_IN_BYTES.set(0);
        return isFinishSuccessful;
    }

    private static synchronized void waitToFinishConsumeBrokerData() {
        if (isAlreadyConsume) {
            isAlreadyConsume = false;
            return;
        }

        isWaitingToConsume = true;
        try {
            log.info("⏳ Waiting to consume...");
            while (isWaitingToConsume) {
                TestService.class.wait(WAIT_TO_CONSUME_TIMEOUT_MS);
            }
            log.info("🎯 Notification received. Continue producing the data... 🏭");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new HttpStreamWaitException(e);
        }
    }

    public static synchronized void notifyProducer(BigDecimal brokerStatusPercentage) {
        if (!brokerStatusPercentage.equals(BigDecimal.valueOf(100))) {
            throw new NotifyProducerException(brokerStatusPercentage);
        }
        log.info("🔔 Notification from consumer app. Broker data is consumed.");
        if (isWaitingBetweenNextAttempt) {
            isAlreadyConsume = true;
        } else {
            TestService.class.notifyAll();
            isWaitingToConsume = false;
        }
    }

    private List<User> prepareData(TestSettingsDto testSettingsDto) {
        List<User> users = new ArrayList<>();
        int totalMessagesToSendInAttempt = testSettingsDto.getNumberOfMessagesToSend() * testSettingsDto.getNumberOfAttempts();
        IntStream.range(0, totalMessagesToSendInAttempt)
                .parallel()
                .forEach(i -> {
                    User user = RandomUtil.generateUser(testSettingsDto.getTestUUID());
                    synchronized (users) {
                        users.add(user);
                    }
                });
        return users;
    }

    private int calculateTotalMessagesToSend(TestSettingsDto testSettingsDto) {
        return testSettingsDto.getNumberOfMessagesToSend() * testSettingsDto.getNumberOfAttempts() * testSettingsDto.getBrokerTypes().size();
    }

    private void sendRecords(BrokerType brokerType,
                             TestSettingsDto testSettingsDto,
                             List<User> users) {
        TestRunnerFunction testRunnerFunction = Optional.ofNullable(testRunnerMap.get(brokerType))
                .orElseThrow(() -> new UnsupportedBrokerTypeException(brokerType));
        int producedDataInTestInBytes = 0;
        int attemptCounter = 1;
        int numberOfAttempts = testSettingsDto.getNumberOfAttempts();
        int numberOfMessagesToSend = testSettingsDto.getNumberOfMessagesToSend();
        long delayInMilliseconds = testSettingsDto.getDelayInMilliseconds();
        UUID testUUID = testSettingsDto.getTestUUID();
        CURRENT_BROKER_TO_SEND.set(numberOfMessagesToSend * numberOfAttempts);
        CURRENT_BROKER_OBTAINED.set(0);

        while (attemptCounter <= numberOfAttempts) {
            int messagesObtainedInAttempt = 1;
            while (messagesObtainedInAttempt <= numberOfMessagesToSend) {
                User user = users.get(CURRENT_BROKER_OBTAINED.get());
                int payloadSizeInBytes = getObjectSizeInBytes(user);
                TOTAL_MESSAGES_OBTAINED.incrementAndGet();
                CURRENT_BROKER_OBTAINED.incrementAndGet();
                TOTAL_PRODUCED_DATA_SIZE_IN_BYTES.addAndGet(payloadSizeInBytes);
                producedDataInTestInBytes += payloadSizeInBytes;
                testRunnerFunction.apply(testUUID,
                        attemptCounter,
                        messagesObtainedInAttempt,
                        user,
                        payloadSizeInBytes,
                        producedDataInTestInBytes);
                messagesObtainedInAttempt++;
            }
            attemptCounter++;
            if (attemptCounter < numberOfAttempts) {
                waitInMilliseconds(delayInMilliseconds);
            }
        }
    }

    private static void waitInMilliseconds(Long delayInMilliseconds) {
        isWaitingBetweenNextAttempt = true;
        try {
            Thread.sleep(delayInMilliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DelayBetweenTestException(e);
        }
        isWaitingBetweenNextAttempt = false;
    }

    @FunctionalInterface
    private interface TestRunnerFunction {
        void apply(UUID testUUID, int numberOfAttempt, int messagesObtainedInTest, User user, Integer payloadSizeInBytes, Integer producedDataInTestInBytes);
    }
}
