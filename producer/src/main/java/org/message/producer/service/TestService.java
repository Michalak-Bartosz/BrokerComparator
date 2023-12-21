package org.message.producer.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.message.model.User;
import org.message.producer.dto.TestSettingsDto;
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
                BrokerType.KAFKA, kafkaProducer::sendRecords,
                BrokerType.RABBITMQ, rabbitMqProducer::sendRecords);
    }

    @Transactional
    public synchronized void performTest(TestSettingsDto testSettingsDto) {
        List<User> users = new ArrayList<>();
        prepareData(testSettingsDto.getTestUUID(), testSettingsDto.getNumberOfMessagesToSend(), users);
        testSettingsDto.getBrokerTypes().forEach(brokerType ->
                Optional.ofNullable(testRunnerMap.get(brokerType)).orElseThrow(() -> new UnsupportedBrokerTypeException(brokerType))
                        .apply(testSettingsDto.getTestUUID(), testSettingsDto.getNumberOfMessagesToSend(), users));
    }

    private static void prepareData(UUID testUUID, Integer numberOfMessagesToSend, List<User> users) {
        int messagesObtained = 1;
        while (messagesObtained <= numberOfMessagesToSend) {
            User user = RandomUtil.generateUser(testUUID);
            users.add(user);
            messagesObtained++;
        }
    }

    @FunctionalInterface
    private interface TestRunnerFunction {
        void apply(UUID testUUID, Integer numberOfMessagesToSend, List<User> users);
    }
}
