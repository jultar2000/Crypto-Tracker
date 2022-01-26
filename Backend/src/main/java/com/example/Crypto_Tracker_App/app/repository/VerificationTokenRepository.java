package com.example.Crypto_Tracker_App.app.repository;

import com.example.Crypto_Tracker_App.app.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String verificationToken);
}
