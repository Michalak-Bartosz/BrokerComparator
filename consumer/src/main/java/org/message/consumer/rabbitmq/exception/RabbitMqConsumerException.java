package org.message.consumer.rabbitmq.exception;

public class RabbitMqConsumerException extends RuntimeException {

    public RabbitMqConsumerException(Exception e) {
        super(e);
    }
}
