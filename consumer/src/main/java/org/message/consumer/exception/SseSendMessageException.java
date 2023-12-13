package org.message.consumer.exception;

public class SseSendMessageException extends RuntimeException {
    public SseSendMessageException(Exception e) {
        super("Sse send message Exception!", e);
    }
}
