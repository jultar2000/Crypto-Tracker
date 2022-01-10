package com.example.Crypto_Tracker_App.app.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Configuration
public class JwtSecretKey {

    @Value("${app.security.secretKey}")
    String secretKey;

    @Value("${app.security.expirationTime}")
    Long expirationTime;

    @Bean
    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public Long getExpirationTime() {
        return expirationTime;
    }
}


