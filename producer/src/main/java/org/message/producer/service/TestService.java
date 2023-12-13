package org.message.producer.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.message.producer.dto.TestSettingsDto;
import org.message.producer.kafka.KafkaProducer;
import org.message.producer.util.BrokerType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TestService {
    private final KafkaProducer kafkaProducer;
    private Map<BrokerType, TestRunnerFunction> testRunnerMap;

    @PostConstruct
    private void initTestRunnerMap() {
        this.testRunnerMap = Map.of(
                BrokerType.KAFKA, kafkaProducer::sendRecords
        );
    }

    @Transactional
    public synchronized void performTest(TestSettingsDto testSettingsDto) {
        testRunnerMap.get(testSettingsDto.getBrokerType())
                .apply(testSettingsDto.getTestUUID(), testSettingsDto.getNumberOfMessagesToSend());
    }

    @FunctionalInterface
    private interface TestRunnerFunction {
        void apply(UUID testUUID, Integer numberOfMessagesToSend);
    }
}
