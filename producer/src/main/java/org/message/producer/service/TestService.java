package org.message.producer.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.message.model.User;
import org.message.producer.controller.HttpStreamController;
import org.message.producer.dto.FinishTestDto;
import org.message.producer.dto.TestSettingsDto;
import org.message.producer.exception.DelayBetweenTestException;
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

@Service
@RequiredArgsConstructor
public class TestService {

    public static final AtomicInteger TOTAL_MESSAGES_TO_SEND = new AtomicInteger();
    public static final AtomicInteger TOTAL_MESSAGES_OBTAINED = new AtomicInteger(0);

    public static BigDecimal getTestStatusPercentage() {
        return BigDecimal.valueOf((TOTAL_MESSAGES_OBTAINED.get() * 100.0) / TOTAL_MESSAGES_TO_SEND.get());
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
        TOTAL_MESSAGES_TO_SEND.set(calculateTotalMessagesToSend(testSettingsDto));
        TOTAL_MESSAGES_OBTAINED.set(0);
        testSettingsDto.getBrokerTypes().forEach(brokerType ->
                sendRecords(brokerType,
                        testSettingsDto,
                        users)
        );
    }

    @Transactional
    public boolean finishTest(FinishTestDto finishTestDto) {
        HttpStreamController.finishHttpStream();
        boolean isFinishSuccessful = finishTestDto.getNumberOfReceivedMessagesProducer() == TOTAL_MESSAGES_OBTAINED.get();
        TOTAL_MESSAGES_OBTAINED.set(0);
        return isFinishSuccessful;
    }

    private List<User> prepareData(TestSettingsDto testSettingsDto) {
        List<User> users = new ArrayList<>();
        int totalMessagesToSendInAttempt = testSettingsDto.getNumberOfMessagesToSend() * testSettingsDto.getNumberOfAttempts();
        int messagesObtained = 1;
        while (messagesObtained <= totalMessagesToSendInAttempt) {
            User user = RandomUtil.generateUser(testSettingsDto.getTestUUID());
            users.add(user);
            messagesObtained++;
        }
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
        int messagesObtainedInTest = 0;
        int attemptCounter = 1;
        int numberOfAttempts = testSettingsDto.getNumberOfAttempts();
        int numberOfMessagesToSend = testSettingsDto.getNumberOfMessagesToSend();
        long delayInMilliseconds = testSettingsDto.getDelayInMilliseconds();
        UUID testUUID = testSettingsDto.getTestUUID();

        while (attemptCounter <= numberOfAttempts) {
            int messagesObtainedInAttempt = 1;
            while (messagesObtainedInAttempt <= numberOfMessagesToSend) {
                TOTAL_MESSAGES_OBTAINED.incrementAndGet();
                User user = users.get(messagesObtainedInTest);
                testRunnerFunction.apply(testUUID,
                        attemptCounter,
                        messagesObtainedInAttempt,
                        user);
                messagesObtainedInAttempt++;
                messagesObtainedInTest++;
            }
            attemptCounter++;
            waitBetweenNextTest(numberOfAttempts, delayInMilliseconds);
        }
    }

    public void waitBetweenNextTest(Integer numberOfAttempts, Long delayInMilliseconds) {
        if (numberOfAttempts == 1) {
            return;
        }
        try {
            Thread.sleep(delayInMilliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DelayBetweenTestException(e);
        }
    }

    @FunctionalInterface
    private interface TestRunnerFunction {
        void apply(UUID testUUID, int numberOfAttempt, int messagesObtainedInTest, User user);
    }
}
