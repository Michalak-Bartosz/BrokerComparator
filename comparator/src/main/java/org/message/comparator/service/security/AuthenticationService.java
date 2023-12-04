package org.message.comparator.service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.message.comparator.dto.security.AuthenticationRequestDto;
import org.message.comparator.dto.security.AuthenticationResponseDto;
import org.message.comparator.dto.security.RegisterRequestDto;
import org.message.comparator.entity.DashboardUser;
import org.message.comparator.entity.Token;
import org.message.comparator.entity.TokenType;
import org.message.comparator.exception.UserAlreadyExistException;
import org.message.comparator.repository.DashboardUserRepository;
import org.message.comparator.repository.security.TokenRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) {
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
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
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

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(TokenType.BEARER.name())) {
            return;
        }
        final String refreshToken = authHeader.substring(7);
        final String username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            var user = this.dashboardUserRepository.findByUsername(username)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponseDto.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
