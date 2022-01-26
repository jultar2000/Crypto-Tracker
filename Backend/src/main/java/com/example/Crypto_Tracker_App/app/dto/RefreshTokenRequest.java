package com.example.Crypto_Tracker_App.app.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class RefreshTokenRequest {
    @NotBlank
    private String refreshToken;

    private String username;
}
