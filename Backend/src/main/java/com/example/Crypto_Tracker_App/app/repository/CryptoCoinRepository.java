package com.example.Crypto_Tracker_App.app.repository;

import com.example.Crypto_Tracker_App.app.entity.CryptoCoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CryptoCoinRepository extends JpaRepository<CryptoCoin, Long> {
    Optional<CryptoCoin> findByCoinName(String coinName);

    boolean existsByCoinName(String coinName);

    void deleteByCoinName(String coinName);
}
