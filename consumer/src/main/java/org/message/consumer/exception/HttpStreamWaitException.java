package org.message.consumer.exception;

public class HttpStreamWaitException extends RuntimeException {
    public HttpStreamWaitException(Exception e) {
        super(e);
    }
}
