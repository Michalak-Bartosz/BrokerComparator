package org.message.producer.exception;

public class DelayBetweenTestException extends RuntimeException {

    public DelayBetweenTestException(Exception e) {
        super("Delay between tests exception!", e);
    }
}
