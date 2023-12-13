package org.message.comparator.exception;

public class RefreshTokenException extends RuntimeException {

    public RefreshTokenException() {
        super("Unable to refresh token!");
    }
}
