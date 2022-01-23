package com.example.Crypto_Tracker_App.app.dto;

import lombok.*;

import java.time.Instant;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AuthUserResponse {
    private String authenticationToken;

    private Instant expiresAt;

    private String username;
}
