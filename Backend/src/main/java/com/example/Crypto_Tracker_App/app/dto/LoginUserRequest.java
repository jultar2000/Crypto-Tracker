package com.example.Crypto_Tracker_App.app.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class LoginUserRequest {
    private String username;

    private String password;
}
