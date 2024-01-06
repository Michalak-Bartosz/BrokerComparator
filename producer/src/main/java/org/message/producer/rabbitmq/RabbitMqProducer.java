package org.message.producer.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.message.model.DebugInfo;
import org.message.model.User;
import org.message.model.util.BrokerType;
import org.message.producer.util.StreamMessageUtil;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

import static org.message.producer.service.TestService.getTestStatusPercentage;
import static org.message.producer.util.MetricUtil.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class RabbitMqProducer {

    private static final BrokerType BROKER_TYPE = BrokerType.RABBITMQ;

    private final TopicExchange exchange;
    private final RabbitTemplate rabbitTemplate;
    @Value("${spring.rabbitmq.routing.key.user}")
    private String userQueueRoutingKey;
    @Value("${spring.rabbitmq.routing.key.report}")
    private String reportQueueRoutingKey;
    @Value("${spring.rabbitmq.routing.key.debug-info}")
    private String debugInfoQueueRoutingKey;

    public void sendRecord(UUID testUUID,
                           int numberOfAttempt,
                           int messagesObtainedInAttempt,
                           User user,
                           Integer payloadSizeInBytes,
                           Integer producedDataInTestInBytes) {
        final BigDecimal systemCpuBefore = getSystemCpuUsagePercentage();
        final BigDecimal appCpuBefore = getAppCpuUsagePercentage();

        updateBrokerType(user);
        rabbitTemplate.convertAndSend(exchange.getName(), userQueueRoutingKey, user);
        log.debug("User UUID sent -> {}", user.getUuid());

        user.getReports().forEach(report -> {
            report.setBrokerType(BROKER_TYPE);
            report.getComments().forEach(comment -> comment.setBrokerType(BROKER_TYPE));
            rabbitTemplate.convertAndSend(exchange.getName(), reportQueueRoutingKey, report);
            log.debug("Report UUID sent -> {}", report.getUuid());
        });

        final BigDecimal systemCpuAfter = getSystemCpuUsagePercentage();
        final BigDecimal appCpuAfter = getAppCpuUsagePercentage();

        final BigDecimal systemAverageCpu = getAverageCpuPercentage(systemCpuBefore, systemCpuAfter);
        final BigDecimal appAverageCpu = getAverageCpuPercentage(appCpuBefore, appCpuAfter);

        DebugInfo debugInfo = new DebugInfo(testUUID,
                user.getUuid(),
                numberOfAttempt,
                BROKER_TYPE,
                getTestStatusPercentage(),
                messagesObtainedInAttempt,
                payloadSizeInBytes,
                producedDataInTestInBytes,
                systemAverageCpu,
                appAverageCpu);
        StreamMessageUtil.addMessage(debugInfo);
        rabbitTemplate.convertAndSend(exchange.getName(), debugInfoQueueRoutingKey, debugInfo);
        log.debug("DebugInfo UUID sent -> {}", debugInfo.getUuid());
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