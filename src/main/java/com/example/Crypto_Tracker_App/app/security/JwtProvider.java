package com.example.Crypto_Tracker_App.app.security;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class JwtProvider {

    private JwtSecretKey jwtSecretKey;

    public String generateToken(Authentication authentication){
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(Date.from((Instant.now())))
                .signWith(jwtSecretKey.getSecretKey())
                .setExpiration(Date.from(Instant.now().plusSeconds(600)))
                .compact();
    }
}
