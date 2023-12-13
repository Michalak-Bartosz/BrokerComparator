package org.message.comparator.controller.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.message.comparator.dto.security.AuthenticationResponseDto;
import org.message.comparator.dto.security.LogInRequestDto;
import org.message.comparator.dto.security.RegisterRequestDto;
import org.message.comparator.service.security.AuthenticationService;
import org.message.comparator.service.security.CookieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.message.comparator.config.ApiConstants.REQUEST_MAPPING_NAME;

@RestController
@RequestMapping(REQUEST_MAPPING_NAME + "/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final CookieService cookieService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(
            @RequestBody RegisterRequestDto registerRequestDto) {
        AuthenticationResponseDto dto = authenticationService.register(registerRequestDto);
        return ResponseEntity.ok()
                .headers(cookieService.getAuthorizationCookieHeaders(dto))
                .body(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(
            @RequestBody LogInRequestDto request) {
        AuthenticationResponseDto dto = authenticationService.logIn(request);
        return ResponseEntity.ok()
                .headers(cookieService.getAuthorizationCookieHeaders(dto))
                .body(dto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        authenticationService.logout(request);
        return ResponseEntity.ok()
                .headers(cookieService.deleteAuthorizationCookieHeaders())
                .build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponseDto> refreshToken(HttpServletRequest request) {
        AuthenticationResponseDto dto = authenticationService.refreshToken(request);
        return ResponseEntity.ok()
                .headers(cookieService.getAuthorizationCookieHeaders(dto))
                .body(dto);
    }
}
