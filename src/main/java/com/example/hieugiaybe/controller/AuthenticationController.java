package com.example.hieugiaybe.controller;

import com.example.hieugiaybe.security.entities.RefreshToken;
import com.example.hieugiaybe.security.entities.User;
import com.example.hieugiaybe.security.service.AuthService;
import com.example.hieugiaybe.security.service.JWTService;
import com.example.hieugiaybe.security.service.RefreshTokenService;
import com.example.hieugiaybe.security.util.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JWTService jwtService;

    public AuthenticationController(AuthService authService, RefreshTokenService refreshTokenService, JWTService jwtService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest, HttpServletResponse response){
        return ResponseEntity.ok(authService.register(registerRequest,response));
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response){
        return ResponseEntity.ok(authService.login(loginRequest,response));
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@CookieValue("refreshToken") String refreshTokenRequest){
        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(refreshTokenRequest);
        User user = refreshToken.getUser();

        String access = jwtService.generateToken(user);

        return ResponseEntity.ok(AuthResponse.builder()
                .accessToken(access)
                .build());
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(HttpServletResponse response){
        return ResponseEntity.ok(authService.logout(response));
    }
}
