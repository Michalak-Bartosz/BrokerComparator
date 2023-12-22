package org.message.producer.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.message.model.DebugInfo;
import org.message.model.Report;
import org.message.model.User;
import org.message.producer.rabbitmq.exception.ChannelBasicPublishException;
import org.message.producer.util.StreamMessageUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import static org.message.producer.service.TestService.*;
import static org.message.producer.util.DebugInfoUtil.generateDebugInfo;
import static org.message.producer.util.MetricUtil.*;

@Component
@Slf4j
public class RabbitMqProducer {

    @Value("${spring.rabbitmq.exchange.user-name}")
    private String userExchangeName;
    @Value("${spring.rabbitmq.exchange.report-name}")
    private String reportExchangeName;
    @Value("${spring.rabbitmq.exchange.debug-info-name}")
    private String debugInfoExchangeName;

    private final ConnectionFactory factory = new ConnectionFactory();
    private static final String BROKER_TYPE = "RABBITMQ";

    public void sendRecord(UUID testUUID,
                           int messagesObtainedInAttempt,
                           User user) {
        final double systemCpuBefore = getSystemCpuUsagePercentage();
        final double appCpuBefore = getAppCpuUsagePercentage();

        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(userExchangeName, BuiltinExchangeType.FANOUT.getType());
            channel.exchangeDeclare(reportExchangeName, BuiltinExchangeType.FANOUT.getType());
            channel.exchangeDeclare(debugInfoExchangeName, BuiltinExchangeType.FANOUT.getType());

            channel.basicPublish(userExchangeName, "", null, user.toString().getBytes(StandardCharsets.UTF_8));
            log.info("User sent -> {}", user);

            sendReports(user, channel);

            final double systemCpuAfter = getSystemCpuUsagePercentage();
            final double appCpuAfter = getAppCpuUsagePercentage();

            final double systemAverageCpu = getAverageCpuPercentage(systemCpuBefore, systemCpuAfter);
            final double appAverageCpu = getAverageCpuPercentage(appCpuBefore, appCpuAfter);

            DebugInfo debugInfo = generateDebugInfo(testUUID,
                    BROKER_TYPE,
                    getTestStatusPercentage(),
                    messagesObtainedInAttempt,
                    systemAverageCpu,
                    appAverageCpu);
            StreamMessageUtil.addMessage(debugInfo);
            channel.basicPublish(debugInfoExchangeName, "", null, user.toString().getBytes(StandardCharsets.UTF_8));
            log.info("DebugInfo sent -> {}", user);

        } catch (IOException | TimeoutException e) {
            throw new ChannelBasicPublishException(e);
        }
    }

    private void sendReports(User user, Channel channel) {
        user.getReports().stream()
                .map(Report::toString)
                .forEach(reportString -> {
                    try {
                        channel.basicPublish(reportExchangeName, "", null, reportString.getBytes(StandardCharsets.UTF_8));
                    } catch (IOException e) {
                        throw new ChannelBasicPublishException(e);
                    }
                    log.info("Report sent -> {}", reportString);
                });
    }
}