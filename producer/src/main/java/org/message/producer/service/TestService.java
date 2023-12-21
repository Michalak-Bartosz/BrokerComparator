package org.message.producer.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.message.model.User;
import org.message.producer.dto.TestSettingsDto;
import org.message.producer.exception.DelayBetweenTestException;
import org.message.producer.exception.UnsupportedBrokerTypeException;
import org.message.producer.kafka.KafkaProducer;
import org.message.producer.rabbitmq.RabbitMqProducer;
import org.message.producer.util.BrokerType;
import org.message.producer.util.RandomUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TestService {

    public static double getTestStatusPercentage(int messagesObtained, int messagesTotal) {
        return (messagesObtained * 100.0) / messagesTotal;
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
        Integer totalMessagesToSend = testSettingsDto.getNumberOfMessagesToSend() * testSettingsDto.getNumberOfAttempts();
        List<User> users = new ArrayList<>();
        prepareData(testSettingsDto.getTestUUID(), totalMessagesToSend, users);
        testSettingsDto.getBrokerTypes().forEach(brokerType ->
                sendRecords(brokerType,
                        testSettingsDto.getTestUUID(),
                        testSettingsDto.getNumberOfMessagesToSend(),
                        testSettingsDto.getNumberOfAttempts(),
                        testSettingsDto.getDelayInMilliseconds(),
                        users)
        );
    }

    private static void prepareData(UUID testUUID, Integer numberOfMessagesToSend, List<User> users) {
        int messagesObtained = 1;
        while (messagesObtained <= numberOfMessagesToSend) {
            User user = RandomUtil.generateUser(testUUID);
            users.add(user);
            messagesObtained++;
        }
    }

    public void sendRecords(BrokerType brokerType,
                            UUID testUUID,
                            Integer numberOfMessagesToSend,
                            Integer numberOfAttempts,
                            Long delayInMilliseconds,
                            List<User> users) {
        TestRunnerFunction testRunnerFunction = Optional.ofNullable(testRunnerMap.get(brokerType))
                .orElseThrow(() -> new UnsupportedBrokerTypeException(brokerType));
        int totalMessagesToSend = numberOfMessagesToSend * numberOfAttempts;
        int totalMessagesObtained = 1;
        int attemptCounter = 0;

        while (attemptCounter < numberOfAttempts) {
            int messagesObtainedInTest = 1;
            while (messagesObtainedInTest <= numberOfMessagesToSend) {
                User user = users.get(totalMessagesObtained - 1);
                testRunnerFunction.apply(testUUID,
                        messagesObtainedInTest,
                        totalMessagesObtained,
                        totalMessagesToSend,
                        user);
                messagesObtainedInTest++;
                totalMessagesObtained++;
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
        void apply(UUID testUUID, int messagesObtainedInTest, int totalMessagesObtained, int totalMessagesToSend, User user);
    }
}
