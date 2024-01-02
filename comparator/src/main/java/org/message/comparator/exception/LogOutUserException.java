package org.message.comparator.exception;

public class LogOutUserException extends RuntimeException{

    public LogOutUserException() {
        super("Access Token does not exist! Can not log out user.");
    }
}
