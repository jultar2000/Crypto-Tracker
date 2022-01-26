package com.example.Crypto_Tracker_App.app.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtProvider {

    private final JwtSecretKey jwtSecretKey;

    @Autowired
    public JwtProvider(JwtSecretKey jwtSecretKey) {
        this.jwtSecretKey = jwtSecretKey;
    }

    public String generateToken(Authentication authentication) {
        var principal =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(Date.from((Instant.now())))
                .signWith(jwtSecretKey.getSecretKey())
                .setExpiration(Date.from(Instant.now().plusSeconds(jwtSecretKey.getExpirationTime())))
                .compact();
    }

    public String generateTokenWithUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from((Instant.now())))
                .signWith(jwtSecretKey.getSecretKey())
                .setExpiration(Date.from(Instant.now().plusSeconds(jwtSecretKey.getExpirationTime())))
                .compact();
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = extractExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public <T> T extractClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Date extractExpirationDateFromToken(String token) {
        return extractClaimFromToken(token, Claims::getExpiration);
    }

    public String extractUsernameFromToken(String token) {
        return extractClaimFromToken(token, Claims::getSubject);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Long getExpirationTime(){
        return jwtSecretKey.getExpirationTime();
    }
}
