package org.message.producer.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.message.model.DebugInfo;
import org.message.model.User;
import org.message.model.util.BrokerType;
import org.message.producer.util.StreamMessageUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

import static org.message.producer.util.MetricUtil.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

    private static final BrokerType BROKER_TYPE = BrokerType.KAFKA;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.kafka.topic.user-data-topic}")
    private String userDataTopic;
    @Value("${spring.kafka.topic.report-data-topic}")
    private String reportDataTopic;
    @Value("${spring.kafka.topic.debug-info-data-topic}")
    private String debugInfoDataTopic;

    public void sendRecord(UUID testUUID,
                           boolean isSync,
                           int numberOfAttempt,
                           long delayInMilliseconds,
                           User user) {
        final BigDecimal systemCpuBefore = getSystemCpuUsagePercentage();
        final BigDecimal appCpuBefore = getAppCpuUsagePercentage();

        updateBrokerType(user);

        kafkaTemplate.send(userDataTopic, user);
        log.debug("User sent -> {}", user);

        user.getReports()
                .forEach(report -> {
                    kafkaTemplate.send(reportDataTopic, report);
                    log.debug("Report sent -> {}", report);
                });

        final BigDecimal systemCpuAfter = getSystemCpuUsagePercentage();
        final BigDecimal appCpuAfter = getAppCpuUsagePercentage();

        final BigDecimal systemAverageCpu = getAverageCpuPercentage(systemCpuBefore, systemCpuAfter);
        final BigDecimal appAverageCpu = getAverageCpuPercentage(appCpuBefore, appCpuAfter);

        DebugInfo debugInfo = new DebugInfo(testUUID,
                user.getUuid(),
                isSync,
                numberOfAttempt,
                delayInMilliseconds,
                BROKER_TYPE,
                user,
                systemAverageCpu,
                appAverageCpu);

        if (isSync) {
            StreamMessageUtil.addMessage(debugInfo);
        }

        kafkaTemplate.send(debugInfoDataTopic, debugInfo);
        log.debug("DebugInfo sent -> {}", debugInfo);
    }

    private void updateBrokerType(User user) {
        user.setBrokerType(BROKER_TYPE);
        user.getAddress().setBrokerType(BROKER_TYPE);
        user.getReports().forEach(report -> {
            report.setBrokerType(BROKER_TYPE);
            report.getComments().forEach(comment -> comment.setBrokerType(BROKER_TYPE));
        });
    }

}
