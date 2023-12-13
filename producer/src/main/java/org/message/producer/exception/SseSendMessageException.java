package org.message.producer.exception;

public class SseSendMessageException extends RuntimeException {
    public SseSendMessageException(Exception e) {
        super("Sse send message Exception!", e);
    }
}
