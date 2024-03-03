package org.message.consumer.rabbitmq;

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
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static org.message.consumer.util.MetricUtil.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class RabbitMqConsumer {

    private final UserService userService;
    private final DebugInfoService debugInfoService;

    @RabbitListener(queues = {"${spring.rabbitmq.queue.user}"})
    public void consumeUsers(User user) {
        userService.saveUserModel(user);
        log.debug("User UUID record received -> {}", user.getUuid());

    }

    @RabbitListener(queues = {"${spring.rabbitmq.queue.report}"})
    public void consumeReports(Report report) {
        log.debug("Report UUID record received -> {}", report.getUuid());
    }

    @RabbitListener(queues = {"${spring.rabbitmq.queue.debug-info}"})
    public void consumeDebugInfo(DebugInfo debugInfo) {

        final BigDecimal systemCpuBefore = getSystemCpuUsagePercentage();
        final BigDecimal appCpuBefore = getAppCpuUsagePercentage();

        TestProgressUtil.incrementTotalMessagesObtained();
        TestProgressUtil.incrementBrokerTotalMessagesObtained(BrokerType.RABBITMQ);

        final BigDecimal systemCpuAfter = getSystemCpuUsagePercentage();
        final BigDecimal appCpuAfter = getAppCpuUsagePercentage();

        final BigDecimal systemAverageCpu = getAverageCpuPercentage(systemCpuBefore, systemCpuAfter);
        final BigDecimal appAverageCpu = getAverageCpuPercentage(appCpuBefore, appCpuAfter);

        DebugInfoUtil.updateDebugInfo(debugInfo, systemAverageCpu, appAverageCpu);

        debugInfoService.saveDebugInfoModel(debugInfo);

        if (Boolean.TRUE.equals(debugInfo.getIsSync())) {
            StreamMessageUtil.addMessage(debugInfo);
        }

        log.debug("DebugInfo UUID record received -> {} test status percentage [ {}% ]", debugInfo.getUuid(), debugInfo.getTestStatusPercentage());
    }
}
