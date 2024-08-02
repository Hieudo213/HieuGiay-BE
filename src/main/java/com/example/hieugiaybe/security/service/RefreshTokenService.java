package com.example.hieugiaybe.security.service;

import com.example.hieugiaybe.security.entities.RefreshToken;
import com.example.hieugiaybe.security.entities.User;
import com.example.hieugiaybe.security.repository.RefreshTokenRepository;
import com.example.hieugiaybe.security.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service

public class RefreshTokenService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }


    public RefreshToken createRefreshToken(String username){
        User user = userRepository.findByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("User not found with email: " + username));
        RefreshToken refreshToken = user.getRefreshToken();
        if (refreshToken == null){
            long refreshTokenValidity = 5*60*60*10000;
            refreshToken = RefreshToken.builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expriationTime(Instant.now().plusMillis(refreshTokenValidity))
                    .user(user)
                    .build();
            refreshTokenRepository.save(refreshToken);
        }

        return refreshToken;
    }

    public RefreshToken verifyRefreshToken(String refreshToken){
        RefreshToken refToken =  refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()-> new RuntimeException("refresh Token not found!"));
        if (refToken.getExpriationTime().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(refToken);
            throw new RuntimeException("Refresh Token expired");
        }

        return refToken;
    }
}
