package org.message.comparator.util;

import lombok.Getter;

@Getter
public enum CookieName {
    ACCESS_TOKEN_COOKIE("accessTokenCookie"), REFRESH_TOKEN_COOKIE("refreshTokenCookie");

    private final String name;

    CookieName(String name) {
        this.name = name;
    }
}
