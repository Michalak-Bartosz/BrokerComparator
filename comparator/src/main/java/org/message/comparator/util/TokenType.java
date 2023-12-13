package org.message.comparator.util;

import lombok.Getter;

@Getter
public enum TokenType {
    BEARER("Bearer");

    private final String name;

    TokenType(String name) {
        this.name = name;
    }
}
