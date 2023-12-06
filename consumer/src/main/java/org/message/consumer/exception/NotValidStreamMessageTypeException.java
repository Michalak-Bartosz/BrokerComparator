package org.message.consumer.exception;

public class NotValidStreamMessageTypeException extends RuntimeException {

    public NotValidStreamMessageTypeException() {
        super("Not valid stream message type!");
    }
}
