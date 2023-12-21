package org.message.consumer.rabbitmq;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.message.consumer.rabbitmq.exception.RabbitMqConsumerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Component
@Slf4j
public class RabbitMqDebugInfoConsumer {
    @Value("${spring.rabbitmq.exchange.debug-info-name}")
    private String debugInfoExchangeName;

    public void receiveDebugInfo() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();) {
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(debugInfoExchangeName, BuiltinExchangeType.FANOUT.getType());
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, debugInfoExchangeName, "");

            log.info("Waiting for messages.");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                log.info("Received '" + message + "'");
            };
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
            });
        } catch (IOException | TimeoutException e) {
            throw new RabbitMqConsumerException(e);
        }
    }
}
