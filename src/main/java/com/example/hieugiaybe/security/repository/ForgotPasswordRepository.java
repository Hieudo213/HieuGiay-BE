package com.example.hieugiaybe.security.repository;

import com.example.hieugiaybe.security.entities.ForgotPassword;
import com.example.hieugiaybe.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {
    @Query("select fb from ForgotPassword fb where fb.otp = ?1 and fb.user =?2")
    Optional<ForgotPassword> findByOtpAndUser(Integer otp, User user);
}
