package org.message.comparator.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String username) {
        super("User already exist! Username: " + username);
    }
}
