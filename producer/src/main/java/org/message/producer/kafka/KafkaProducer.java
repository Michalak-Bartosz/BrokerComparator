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

import java.math.BigDecimal;
import java.util.UUID;

import static org.message.producer.kafka.util.KafkaMessageUtil.getKafkaProducerRecord;
import static org.message.producer.service.TestService.*;
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

    public void sendRecord(UUID testUUID,
                           int numberOfAttempt,
                           int messagesObtainedInAttempt,
                           User user) {
        final BigDecimal systemCpuBefore = getSystemCpuUsagePercentage();
        final BigDecimal appCpuBefore = getAppCpuUsagePercentage();

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

        final BigDecimal systemCpuAfter = getSystemCpuUsagePercentage();
        final BigDecimal appCpuAfter = getAppCpuUsagePercentage();

        final BigDecimal systemAverageCpu = getAverageCpuPercentage(systemCpuBefore, systemCpuAfter);
        final BigDecimal appAverageCpu = getAverageCpuPercentage(appCpuBefore, appCpuAfter);

        DebugInfo debugInfo = generateDebugInfo(testUUID,
                numberOfAttempt,
                BROKER_TYPE,
                getTestStatusPercentage(),
                messagesObtainedInAttempt,
                systemAverageCpu,
                appAverageCpu);
        ProducerRecord<String, DebugInfo> debugInfoKafkaRecord = getKafkaProducerRecord(debugInfoDataTopic, debugInfo);
        StreamMessageUtil.addMessage(debugInfo);
        kafkaTemplateDebugInfoEvent.send(debugInfoKafkaRecord);
        kafkaTemplateDebugInfoEvent.flush();
        log.debug("DebugInfo sent -> {}", debugInfoKafkaRecord);
    }

}
