package org.message.producer.exception;

public class NotValidStreamMessageTypeException extends RuntimeException {

    public NotValidStreamMessageTypeException() {
        super("Not valid stream message type!");
    }
}
