package com.example.hieugiaybe.security.repository;

import com.example.hieugiaybe.security.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    @Query("SELECT r FROM RefreshToken r WHERE r.user.userId = :userId")
    Optional<RefreshToken> findByUserId(@Param("userId") Integer userId);
}
