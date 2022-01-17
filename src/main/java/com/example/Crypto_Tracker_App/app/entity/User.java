package com.example.Crypto_Tracker_App.app.entity;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "userr")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Email is required")
    private String email;

    private String name;

    private String surname;

    private int age;

    private Instant created;

    private boolean enabled;

    @ManyToMany
    @JoinColumn(name = "coins")
    List<CryptoCoin> cryptoCoins;
}
