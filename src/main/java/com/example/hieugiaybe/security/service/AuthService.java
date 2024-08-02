package com.example.hieugiaybe.security.service;

import com.example.hieugiaybe.security.entities.User;
import com.example.hieugiaybe.security.entities.UserRole;
import com.example.hieugiaybe.security.repository.UserRepository;
import com.example.hieugiaybe.security.util.AuthResponse;
import com.example.hieugiaybe.security.util.LoginRequest;
import com.example.hieugiaybe.security.util.LogoutResponse;
import com.example.hieugiaybe.security.util.RegisterRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    public AuthResponse register(RegisterRequest registerRequest, HttpServletResponse response){
        var user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(UserRole.USER)
                .address(registerRequest.getAddress())
                .phone(registerRequest.getPhone())
                .image("mwc.jpg")
                .build();
        User savedUser = userRepository.save(user);
        var accessToken = jwtService.generateToken(savedUser);
        var refreshToken = refreshTokenService.createRefreshToken(savedUser.getEmail());
        addRefreshTokenToCookie(response, refreshToken.getRefreshToken());
        return AuthResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    public AuthResponse login(LoginRequest loginRequest, HttpServletResponse response){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        var user = userRepository.findByEmail(
                        loginRequest.getEmail())
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
        var accessToken = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(loginRequest.getEmail());
        addRefreshTokenToCookie(response, refreshToken.getRefreshToken());
        return AuthResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    private void addRefreshTokenToCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge((int) TimeUnit.DAYS.toSeconds(30));
        response.addCookie(cookie);

        String cookieHeader = String.format("%s=%s; Max-Age=%d; Path=%s; HttpOnly; %s%s",
                cookie.getName(),
                cookie.getValue(),
                cookie.getMaxAge(),
                cookie.getPath(),
                cookie.getSecure() ? "Secure; " : "",
                "SameSite=None");

        response.addHeader("Set-Cookie", cookieHeader);
    }

    public LogoutResponse logout (HttpServletResponse response){
        removeRefreshTokenFromCookie(response);
        return LogoutResponse.builder()
                .message("Logout Successfully!")
                .build();
    }

    private void removeRefreshTokenFromCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        String cookieHeader = String.format("%s=%s; Max-Age=%d; Path=%s; HttpOnly; %s%s",
                cookie.getName(),
                cookie.getValue(),
                cookie.getMaxAge(),
                cookie.getPath(),
                cookie.getSecure() ? "Secure; " : "",
                "SameSite=None");

        response.addHeader("Set-Cookie", cookieHeader);
    }
}
