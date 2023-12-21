package org.message.producer.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.message.model.DebugInfo;
import org.message.model.Report;
import org.message.model.User;
import org.message.producer.util.StreamMessageUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static org.message.producer.kafka.util.KafkaMessageUtil.getKafkaProducerRecord;
import static org.message.producer.service.TestService.getTestStatusPercentage;
import static org.message.producer.util.DebugInfoUtil.generateDebugInfo;
import static org.message.producer.util.MetricUtil.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

    @Value("${spring.kafka.topic.user-data-topic}")
    private String userDataTopic;
    @Value("${spring.kafka.topic.report-data-topic}")
    private String reportDataTopic;
    @Value("${spring.kafka.topic.debug-info-data-topic}")
    private String debugInfoDataTopic;

    private final KafkaTemplate<String, User> kafkaTemplateUserEvent;
    private final KafkaTemplate<String, Report> kafkaTemplateReportEvent;
    private final KafkaTemplate<String, DebugInfo> kafkaTemplateDebugInfoEvent;

    private static final String BROKER_TYPE = "KAFKA";

    public void sendRecords(UUID testUUID, Integer numberOfMessagesToSend, List<User> users) {
        int messagesTotal = numberOfMessagesToSend;
        int messagesObtained = 1;
        while (messagesObtained <= numberOfMessagesToSend) {
            User user = users.get(messagesObtained - 1);
            sendRecord(testUUID, messagesObtained, messagesTotal, user);
            messagesObtained++;
        }
    }

    private void sendRecord(UUID testUUID, int messagesObtained, int messagesTotal, User user) {
        final double systemCpuBefore = getSystemCpuUsagePercentage();
        final double appCpuBefore = getAppCpuUsagePercentage();

        ProducerRecord<String, User> userKafkaRecord = getKafkaProducerRecord(userDataTopic, user);

        StreamMessageUtil.addMessage(user);
        kafkaTemplateUserEvent.send(userKafkaRecord);
        kafkaTemplateUserEvent.flush();
        log.debug("User sent -> {}", userKafkaRecord);

        user.getReports().stream()
                .map(report -> {
                    StreamMessageUtil.addMessage(report);
                    return getKafkaProducerRecord(reportDataTopic, report);
                })
                .forEach(reportProducerRecord -> {
                    kafkaTemplateReportEvent.send(reportProducerRecord);
                    log.debug("Report sent -> {}", reportProducerRecord);
                });
        kafkaTemplateReportEvent.flush();

        final double systemCpuAfter = getSystemCpuUsagePercentage();
        final double appCpuAfter = getAppCpuUsagePercentage();

        final double systemAverageCpu = getAverageCpuPercentage(systemCpuBefore, systemCpuAfter);
        final double appAverageCpu = getAverageCpuPercentage(appCpuBefore, appCpuAfter);

        DebugInfo debugInfo = generateDebugInfo(testUUID,
                BROKER_TYPE,
                getTestStatusPercentage(messagesObtained, messagesTotal),
                messagesObtained,
                systemAverageCpu,
                appAverageCpu);
        ProducerRecord<String, DebugInfo> debugInfoKafkaRecord = getKafkaProducerRecord(debugInfoDataTopic, debugInfo);
        StreamMessageUtil.addMessage(debugInfo);
        kafkaTemplateDebugInfoEvent.send(debugInfoKafkaRecord);
        kafkaTemplateDebugInfoEvent.flush();
        log.debug("DebugInfo sent -> {}", debugInfoKafkaRecord);
    }

}
