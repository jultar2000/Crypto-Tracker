package com.example.Crypto_Tracker_App.app.entity;

import lombok.*;
import javax.persistence.*;
import java.time.Instant;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "token")
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private Instant expiryDate;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;
}
