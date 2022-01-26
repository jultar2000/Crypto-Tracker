package com.example.Crypto_Tracker_App.app.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationEmail {
    private String subject;

    private String toEmail;

    private String body;
}
