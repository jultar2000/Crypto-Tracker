package com.example.Crypto_Tracker_App.app.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Configuration
@ConfigurationProperties(prefix = "app.security")
public class JwtSecretKey {

    String secretKey;

   @Bean
    public SecretKey getSecretKey(){
       return Keys.hmacShaKeyFor(secretKey.getBytes());
   }
}
