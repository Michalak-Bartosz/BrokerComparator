package org.message.consumer.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.message.consumer.service.DebugInfoService;
import org.message.consumer.service.UserService;
import org.message.consumer.util.DebugInfoUtil;
import org.message.consumer.util.StreamMessageUtil;
import org.message.model.DebugInfo;
import org.message.model.Report;
import org.message.model.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static org.message.consumer.service.TestService.TOTAL_MESSAGES_OBTAINED;
import static org.message.consumer.util.metric.MetricUtil.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final UserService userService;
    private final DebugInfoService debugInfoService;

    @KafkaListener(topicPartitions = @TopicPartition(topic = "${spring.kafka.topic.user-data-topic}",
            partitions = "0"),
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeUsers(@Payload User user,
                             @Header(KafkaHeaders.PARTITION) String partition,
                             @Header("event_produced_time") String producedTime,
                             @Header("record_type") String recordType) {
        userService.saveUserModel(user);
        StreamMessageUtil.addMessage(user);
        log.debug("User record from partition [ {} ] produced time [ {} ] record type [ {} ] received -> {}", partition, producedTime, recordType, user.getUuid());
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "${spring.kafka.topic.report-data-topic}",
            partitions = "0"),
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeReports(@Payload Report report,
                               @Header(KafkaHeaders.PARTITION) String partition,
                               @Header("event_produced_time") String producedTime,
                               @Header("record_type") String recordType) {
        StreamMessageUtil.addMessage(report);
        log.debug("Report record from partition [ {} ] produced time [ {} ] record type [ {} ] received -> {}", partition, producedTime, recordType, report.getUuid());
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "${spring.kafka.topic.debug-info-data-topic}",
            partitions = "0"),
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeDebugInfo(@Payload DebugInfo debugInfo,
                                 @Header(KafkaHeaders.PARTITION) String partition,
                                 @Header("event_produced_time") String producedTime,
                                 @Header("record_type") String recordType) {
        final BigDecimal systemCpuBefore = getSystemCpuUsagePercentage();
        final BigDecimal appCpuBefore = getAppCpuUsagePercentage();

        TOTAL_MESSAGES_OBTAINED.incrementAndGet();

        final BigDecimal systemCpuAfter = getSystemCpuUsagePercentage();
        final BigDecimal appCpuAfter = getAppCpuUsagePercentage();

        final BigDecimal systemAverageCpu = getAverageCpuPercentage(systemCpuBefore, systemCpuAfter);
        final BigDecimal appAverageCpu = getAverageCpuPercentage(appCpuBefore, appCpuAfter);

        DebugInfoUtil.updateDebugInfo(debugInfo,
                systemAverageCpu,
                appAverageCpu);

        debugInfoService.saveDebugInfoModel(debugInfo);
        StreamMessageUtil.addMessage(debugInfo);
        log.debug("DebugInfo record from partition [ {} ] produced time [ {} ] record type [ {} ] received -> {} test status percentage [ {}% ]", partition, producedTime, recordType, debugInfo.getUuid(), debugInfo.getTestStatusPercentage());

    }
}
