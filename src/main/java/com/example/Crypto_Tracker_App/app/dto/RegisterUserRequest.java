package com.example.Crypto_Tracker_App.app.dto;

import com.example.Crypto_Tracker_App.app.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequest {

    private String username;

    private String password;

    private String email;

    public static User dtoToEntityMapper(RegisterUserRequest registerUserRequest){
        return User.builder()
                .username(registerUserRequest.getUsername())
                .password(registerUserRequest.getPassword())
                .email(registerUserRequest.getEmail())
                .authenticated(false)
                .created(Instant.now())
                .build();
    }
}
