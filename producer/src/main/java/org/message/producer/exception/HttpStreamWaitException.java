package org.message.producer.exception;

public class HttpStreamWaitException extends RuntimeException {
    public HttpStreamWaitException(Exception e) {
        super(e);
    }
}
