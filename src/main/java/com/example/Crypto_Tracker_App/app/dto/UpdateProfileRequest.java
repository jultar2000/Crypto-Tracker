package com.example.Crypto_Tracker_App.app.dto;

import com.example.Crypto_Tracker_App.app.entity.User;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UpdateProfileRequest {

    private String name;

    private String surname;

    private int age;

    public static void dtoToEntityUpdater(UpdateProfileRequest request, User user){
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setAge(request.getAge());
    }
}
