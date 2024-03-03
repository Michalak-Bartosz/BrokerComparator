package org.message.consumer.exception;

public class WaitToFinishConsumeException extends RuntimeException {
    public WaitToFinishConsumeException(Exception e) {
        super("Wait to finish consume exception!", e);
    }
}
