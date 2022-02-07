package com.example.Crypto_Tracker_App.app.dto;

import com.example.Crypto_Tracker_App.app.entity.CryptoCoin;
import com.example.Crypto_Tracker_App.app.entity.User;
import lombok.*;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GetUserResponse {

    private String name;

    private String surname;

    private int age;

    private String email;

    public static GetUserResponse entityToDtoMapper(User user) {
        return  GetUserResponse.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .age(user.getAge())
                .email(user.getEmail())
                .build();
    }
}
