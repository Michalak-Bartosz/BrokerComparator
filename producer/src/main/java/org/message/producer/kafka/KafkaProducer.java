package org.message.producer.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.message.model.DebugInfo;
import org.message.model.Report;
import org.message.model.User;
import org.message.producer.dto.TestSettingDto;
import org.message.producer.util.DebugInfoUtil;
import org.message.producer.util.RandomUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.message.producer.kafka.util.KafkaMessageUtil.getKafkaProducerRecord;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

    @Value("${spring.kafka.topic.identification-data-topic}")
    private String identificationTopicName;

    private final KafkaTemplate<String, User> kafkaTemplateUserEvent;
    private final KafkaTemplate<String, Report> kafkaTemplateReportEvent;
    private final KafkaTemplate<String, DebugInfo> kafkaTemplateDebugInfoEvent;

    public void startTest(TestSettingDto testSettingDto) {
        final double total = testSettingDto.getNumberOfMessagesToSend();
        double numberOfMessagesToSend = testSettingDto.getNumberOfMessagesToSend();
        while (numberOfMessagesToSend > 0) {
            double testStatus = getTestStatusPercentage(numberOfMessagesToSend, total);
            sendRecord(testStatus);
            numberOfMessagesToSend--;
        }
    }

    private double getTestStatusPercentage(double obtained, double total) {
        return obtained * 100 / total;
    }

    private void sendRecord(double testStatus) {
        final UUID testUUID = UUID.randomUUID();
        User user = RandomUtil.generateUser(testUUID);
        ProducerRecord<String, User> userKafkaRecord = getKafkaProducerRecord(identificationTopicName, user);

        kafkaTemplateUserEvent.send(userKafkaRecord);
        kafkaTemplateUserEvent.flush();
        log.debug("User sent -> {}", userKafkaRecord);

        user.getReports().stream()
                .map(report -> getKafkaProducerRecord(identificationTopicName, report))
                .forEach(reportProducerRecord -> {
                    kafkaTemplateReportEvent.send(reportProducerRecord);
                    log.debug("Report sent -> {}", reportProducerRecord);
                });
        kafkaTemplateReportEvent.flush();

        DebugInfo debugInfo = DebugInfoUtil.generateDebugInfo(testUUID, testStatus);
        ProducerRecord<String, DebugInfo> debugInfoKafkaRecord = getKafkaProducerRecord(identificationTopicName, debugInfo);

        kafkaTemplateDebugInfoEvent.send(debugInfoKafkaRecord);
        kafkaTemplateDebugInfoEvent.flush();
        log.debug("DebugInfo sent -> {}", debugInfoKafkaRecord);
    }
}
