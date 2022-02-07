package com.example.Crypto_Tracker_App.app.dto;

import com.example.Crypto_Tracker_App.app.entity.User;
import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GetUsersResponse {

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    public static class UserDTO {
        private String name;

        private String surname;

        private int age;

        private String email;
    }

    private List<UserDTO> users;

    public static GetUsersResponse entityToDtoMapper(Collection<User> given_users) {
        GetUsersResponseBuilder response = GetUsersResponse.builder();

        List<UserDTO> collect = given_users.stream()
                .map(user ->
                        UserDTO.builder()
                                .name(user.getName())
                                .surname(user.getSurname())
                                .age(user.getAge())
                                .email(user.getEmail())
                                .build())
                .collect(Collectors.toList());

        return response.users(collect).build();
    }
}
