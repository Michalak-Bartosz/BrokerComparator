package org.message.comparator.service.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.message.comparator.dto.security.AuthenticationResponseDto;
import org.message.comparator.dto.security.LogInRequestDto;
import org.message.comparator.dto.security.RegisterRequestDto;
import org.message.comparator.entity.auth.DashboardUser;
import org.message.comparator.entity.auth.Token;
import org.message.comparator.exception.*;
import org.message.comparator.repository.dashboard.DashboardUserRepository;
import org.message.comparator.repository.security.TokenRepository;
import org.message.comparator.util.CookieName;
import org.message.comparator.util.TokenType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final DashboardUserRepository dashboardUserRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDto register(RegisterRequestDto request) {
        if (request.getUsername() == null || request.getPassword() == null) {
            throw new RegisterRequestNullException(request);
        }

        Optional<DashboardUser> user = dashboardUserRepository.findByUsername(request.getUsername());
        if (user.isPresent()) {
            throw new UserAlreadyExistException(request.getUsername());
        }

        var newUser = DashboardUser.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        var savedUser = dashboardUserRepository.save(newUser);
        var jwtToken = jwtService.generateToken(newUser);
        var refreshToken = jwtService.generateRefreshToken(newUser);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponseDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponseDto logIn(LogInRequestDto request) {
        if (request.getUsername() == null || request.getPassword() == null) {
            throw new LogInRequestNullException(request);
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var dashboardUser = dashboardUserRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(dashboardUser);
        var refreshToken = jwtService.generateRefreshToken(dashboardUser);
        revokeAllUserTokens(dashboardUser);
        saveUserToken(dashboardUser, jwtToken);
        return AuthenticationResponseDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(DashboardUser dashboardUser, String jwtToken) {
        var token = Token.builder()
                .dashboardUser(dashboardUser)
                .jwtToken(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        if (tokenRepository.findByJwtToken(token.getJwtToken()).isEmpty()) {
            tokenRepository.save(token);
        }
    }

    private void revokeAllUserTokens(DashboardUser user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public AuthenticationResponseDto refreshToken(HttpServletRequest request) {
        final Optional<Cookie> optionalRefreshToken = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals(CookieName.REFRESH_TOKEN_COOKIE.getName())).findFirst();
        if (optionalRefreshToken.isEmpty()) {
            throw new RefreshTokenException();
        }
        final String refreshToken = optionalRefreshToken.get().getValue();
        final String username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            var user = this.dashboardUserRepository.findByUsername(username)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                return AuthenticationResponseDto.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
            }
        }
        throw new RefreshTokenException();
    }

    public void logout(HttpServletRequest request) {
        final Optional<Cookie> optionalAccessToken = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals(CookieName.ACCESS_TOKEN_COOKIE.getName())).findFirst();
        if (optionalAccessToken.isEmpty()) {
            throw new LogOutUserException();
        }
        final String accessToken = optionalAccessToken.get().getValue();
        final String username = jwtService.extractUsername(accessToken);
        if (username != null) {
            var user = this.dashboardUserRepository.findByUsername(username)
                    .orElseThrow();
            if (jwtService.isTokenValid(accessToken, user)) {
                revokeAllUserTokens(user);
            }
        }
    }
}
