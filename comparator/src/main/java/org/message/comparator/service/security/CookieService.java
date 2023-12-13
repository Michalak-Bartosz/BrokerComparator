package org.message.comparator.service.security;

import lombok.extern.slf4j.Slf4j;
import org.message.comparator.dto.security.AuthenticationResponseDto;
import org.message.comparator.util.CookieName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CookieService {

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public HttpHeaders getAuthorizationCookieHeaders(AuthenticationResponseDto authenticationResponseDto) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.addAll(HttpHeaders.SET_COOKIE,
                List.of(
                        createNewCookieString(CookieName.ACCESS_TOKEN_COOKIE, authenticationResponseDto.getAccessToken()),
                        createNewCookieString(CookieName.REFRESH_TOKEN_COOKIE, authenticationResponseDto.getRefreshToken())));
        return responseHeaders;
    }

    public HttpHeaders deleteAuthorizationCookieHeaders() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.addAll(HttpHeaders.SET_COOKIE,
                List.of(deleteCookieString(CookieName.ACCESS_TOKEN_COOKIE),
                        deleteCookieString(CookieName.REFRESH_TOKEN_COOKIE)));
        return responseHeaders;

    }

    private String createNewCookieString(CookieName cookieName, String cookieValue) {
        return ResponseCookie.from(cookieName.getName(), cookieValue)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(getExpirationBasedOnCookie(cookieName))
                .build()
                .toString();
    }

    private String deleteCookieString(CookieName cookieName) {
        return ResponseCookie.from(cookieName.getName(), "")
                .build()
                .toString();
    }

    private long getExpirationBasedOnCookie(CookieName cookieName) {
        return switch (cookieName) {
            case ACCESS_TOKEN_COOKIE -> TimeUnit.MILLISECONDS.toSeconds(jwtExpiration);
            case REFRESH_TOKEN_COOKIE -> TimeUnit.MILLISECONDS.toSeconds(refreshExpiration);
        };
    }
}
