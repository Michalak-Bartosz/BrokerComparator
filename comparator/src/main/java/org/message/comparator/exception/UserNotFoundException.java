package org.message.comparator.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(UUID userUUID) {
        super(String.format("User with uuid: [%s] not found!", userUUID));
    }
}
