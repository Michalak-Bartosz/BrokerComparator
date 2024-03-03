package org.message.consumer.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.message.consumer.service.DebugInfoService;
import org.message.consumer.service.UserService;
import org.message.consumer.util.DebugInfoUtil;
import org.message.consumer.util.StreamMessageUtil;
import org.message.consumer.util.TestProgressUtil;
import org.message.model.DebugInfo;
import org.message.model.Report;
import org.message.model.User;
import org.message.model.util.BrokerType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static org.message.consumer.util.MetricUtil.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final UserService userService;
    private final DebugInfoService debugInfoService;

    @KafkaListener(topics = "${spring.kafka.topic.user-data-topic}")
    public void consumeUsers(@Payload User user,
                             @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        userService.saveUserModel(user);
        log.debug("User record received -> PARTITION: {} UUID: {}", partition, user.getUuid());
    }

    @KafkaListener(topics = "${spring.kafka.topic.report-data-topic}")
    public void consumeReports(@Payload Report report,
                               @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        log.debug("Report record received -> PARTITION: {} UUID: {}", partition, report.getUuid());
    }

    @KafkaListener(topics = "${spring.kafka.topic.debug-info-data-topic}")
    public void consumeDebugInfo(@Payload DebugInfo debugInfo,
                                 @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        final BigDecimal systemCpuBefore = getSystemCpuUsagePercentage();
        final BigDecimal appCpuBefore = getAppCpuUsagePercentage();

        TestProgressUtil.incrementTotalMessagesObtained();
        TestProgressUtil.incrementBrokerTotalMessagesObtained(BrokerType.KAFKA);

        final BigDecimal systemCpuAfter = getSystemCpuUsagePercentage();
        final BigDecimal appCpuAfter = getAppCpuUsagePercentage();

        final BigDecimal systemAverageCpu = getAverageCpuPercentage(systemCpuBefore, systemCpuAfter);
        final BigDecimal appAverageCpu = getAverageCpuPercentage(appCpuBefore, appCpuAfter);

        DebugInfoUtil.updateDebugInfo(debugInfo,
                systemAverageCpu,
                appAverageCpu);

        debugInfoService.saveDebugInfoModel(debugInfo);

        if (Boolean.TRUE.equals(debugInfo.getIsSync())) {
            StreamMessageUtil.addMessage(debugInfo);
        }

        log.debug("DebugInfo record received -> PARTITION: {} UUID: {} test status percentage [ {}% ]", partition, debugInfo.getUuid(), debugInfo.getTestStatusPercentage());
    }
}
