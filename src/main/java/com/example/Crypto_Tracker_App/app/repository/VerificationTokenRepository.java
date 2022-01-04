package com.example.Crypto_Tracker_App.app.repository;

import com.example.Crypto_Tracker_App.app.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
}
