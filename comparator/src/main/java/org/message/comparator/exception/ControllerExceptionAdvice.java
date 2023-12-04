package org.message.comparator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(value = {UserAlreadyExistException.class})
    public ResponseEntity<HttpStatus> userAlreadyExist() {
        return ResponseEntity.badRequest().build();
    }
}
