package org.message.producer.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.message.model.User;
import org.message.model.util.BrokerType;
import org.message.producer.controller.HttpStreamController;
import org.message.producer.dto.FinishTestDto;
import org.message.producer.dto.TestSettingsDto;
import org.message.producer.exception.DelayBetweenTestException;
import org.message.producer.exception.HttpStreamWaitException;
import org.message.producer.exception.UnsupportedBrokerTypeException;
import org.message.producer.kafka.KafkaProducer;
import org.message.producer.rabbitmq.RabbitMqProducer;
import org.message.producer.util.RandomUtil;
import org.message.producer.util.TestProgressUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService {
    private static final long WAIT_TO_CONSUME_TIMEOUT_MS = 5000;

    private static final AtomicBoolean isWaitingToConsume = new AtomicBoolean(false);
    private static final AtomicBoolean isAlreadyConsume = new AtomicBoolean(false);
    private static final AtomicBoolean isWaitingBetweenNextAttempt = new AtomicBoolean(false);

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
    public void performTest(TestSettingsDto testSettingsDto) {
        List<User> users = prepareData(testSettingsDto);
        List<BrokerType> brokerTypes = testSettingsDto.getBrokerTypes();
        int brokerTypeSize = testSettingsDto.getBrokerTypes().size();
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
        boolean isFinishSuccessful = finishTestDto.getNumberOfReceivedMessagesProducer() == TestProgressUtil.getTotalMessagesObtained();
        TestProgressUtil.setTotalMessagesObtained(0);
        TestProgressUtil.setCurrentBrokerTotalProducedDataSizeInBytes(0);
        return isFinishSuccessful;
    }

    private static synchronized void waitToFinishConsumeBrokerData() {
        if (isAlreadyConsume.get()) {
            isAlreadyConsume.set(false);
            return;
        }

        isWaitingToConsume.set(true);
        try {
            log.info("⏳ Waiting to consume...");
            while (isWaitingToConsume.get()) {
                TestService.class.wait(WAIT_TO_CONSUME_TIMEOUT_MS);
            }
            log.info("🎯 Notification received. Continue producing the data... 🏭");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new HttpStreamWaitException(e);
        }
    }

    public static synchronized void notifyProducer(org.message.model.util.BrokerType brokerType) {
        log.info("🔔 Notification from consumer app. Broker {} consumed all data!", brokerType);
        if (isWaitingBetweenNextAttempt.get()) {
            isAlreadyConsume.set(true);
        } else {
            TestService.class.notifyAll();
            isWaitingToConsume.set(false);
        }
    }

    private List<User> prepareData(TestSettingsDto testSettingsDto) {
        log.info("🏭 Preparing the data...");
        List<User> users = new ArrayList<>();
        IntStream.range(0, testSettingsDto.getNumberOfMessagesToSend())
                .parallel()
                .forEach(i -> {
                    User user = RandomUtil.generateUser(testSettingsDto.getTestUUID());
                    synchronized (users) {
                        users.add(user);
                    }
                });
        log.info("🪪 The data is ready!");
        return users;
    }

    private void sendRecords(BrokerType brokerType,
                             TestSettingsDto testSettingsDto,
                             List<User> users) {
        log.info("🏁 Start test for broker: {}", brokerType);
        TestRunnerFunction testRunnerFunction = Optional.ofNullable(testRunnerMap.get(brokerType))
                .orElseThrow(() -> new UnsupportedBrokerTypeException(brokerType));
        AtomicInteger attemptCounter = new AtomicInteger(1);
        int numberOfAttempts = testSettingsDto.getNumberOfAttempts();
        int numberOfMessagesToSend = testSettingsDto.getNumberOfMessagesToSend();
        long delayInMilliseconds = testSettingsDto.getDelayInMilliseconds();
        boolean isSync = testSettingsDto.isSync();
        UUID testUUID = testSettingsDto.getTestUUID();

        TestProgressUtil.setCurrentBrokerTotalProducedDataSizeInBytes(0);
        TestProgressUtil.setCurrentBrokerMessagesToSend(numberOfMessagesToSend * numberOfAttempts);
        TestProgressUtil.setCurrentBrokerMessagesObtained(0);

        while (attemptCounter.get() <= numberOfAttempts) {
            TestProgressUtil.setMessageObtainedInAttempt(0);

            if (isSync) {
                sendSync(users, testRunnerFunction, testUUID, attemptCounter.get(), delayInMilliseconds);
            } else {
                sendAsync(users, testRunnerFunction, testUUID, attemptCounter.get(), delayInMilliseconds);
            }
            attemptCounter.incrementAndGet();
            if (attemptCounter.get() < numberOfAttempts) {
                waitInMilliseconds(delayInMilliseconds);
            }
        }
        log.info("🏁 Finish test for broker: {}", brokerType);
    }

    private void sendSync(List<User> users,
                          TestRunnerFunction testRunnerFunction,
                          UUID testUUID,
                          int numberOfAttempt,
                          long delayInMilliseconds) {
        users.forEach(user -> testRunnerFunction.apply(testUUID,
                true,
                numberOfAttempt,
                delayInMilliseconds,
                user));
    }

    private void sendAsync(List<User> users,
                           TestRunnerFunction testRunnerFunction,
                           UUID testUUID,
                           int numberOfAttempt,
                           long delayInMilliseconds) {
        users.stream()
                .parallel()
                .forEach(user -> testRunnerFunction.apply(testUUID,
                        false,
                        numberOfAttempt,
                        delayInMilliseconds,
                        user));
    }

    private static void waitInMilliseconds(Long delayInMilliseconds) {
        isWaitingBetweenNextAttempt.set(true);
        try {
            Thread.sleep(delayInMilliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DelayBetweenTestException(e);
        }
        isWaitingBetweenNextAttempt.set(false);
    }

    @FunctionalInterface
    private interface TestRunnerFunction {
        void apply(UUID testUUID, boolean isSync, int numberOfAttempt, long delayInMilliseconds, User user);
    }
}
