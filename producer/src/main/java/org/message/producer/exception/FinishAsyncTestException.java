package org.message.producer.exception;

import org.message.producer.dto.FinishAsyncTestDto;

public class FinishAsyncTestException extends RuntimeException{
    public FinishAsyncTestException(String message) {
        super("Finish async test exception! Error message from consumer: " + message);
    }
}
