package org.message.comparator.exception;

import org.message.comparator.dto.security.LogInRequestDto;

public class LogInRequestNullException extends RuntimeException{
    public LogInRequestNullException(LogInRequestDto logInRequestDto) {
        super("LogIn request params are null! Request: " + logInRequestDto.toString());
    }
}
