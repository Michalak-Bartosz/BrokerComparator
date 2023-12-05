package org.message.comparator.exception;

import org.message.comparator.dto.security.RegisterRequestDto;

public class RegisterRequestNullException extends RuntimeException{
    public RegisterRequestNullException(RegisterRequestDto registerRequestDto) {
        super("Registration request params are null! Request: " + registerRequestDto.toString());
    }
}
