package com.example.Crypto_Tracker_App.app.dto;

import com.example.Crypto_Tracker_App.app.entity.User;
import lombok.*;

import java.time.Instant;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class RegisterUserRequest {
    private String username;

    private String password;

    private String email;

    public static User dtoToEntityMapper(RegisterUserRequest registerUserRequest) {
        return User.builder()
                .username(registerUserRequest.getUsername())
                .password(registerUserRequest.getPassword())
                .email(registerUserRequest.getEmail())
                .enabled(false)
                .created(Instant.now())
                .build();
    }
}
