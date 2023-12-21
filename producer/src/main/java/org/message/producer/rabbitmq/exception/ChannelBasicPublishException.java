package org.message.producer.rabbitmq.exception;

public class ChannelBasicPublishException extends RuntimeException {

    public ChannelBasicPublishException(Exception e) {
        super(e);
    }
}
