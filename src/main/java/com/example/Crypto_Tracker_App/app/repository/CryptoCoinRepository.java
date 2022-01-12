package com.example.Crypto_Tracker_App.app.repository;

import com.example.Crypto_Tracker_App.app.entity.CryptoCoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CryptoCoinRepository extends JpaRepository<CryptoCoin, String> {
    Optional<CryptoCoin> findByCoinName(String coinName);
}
