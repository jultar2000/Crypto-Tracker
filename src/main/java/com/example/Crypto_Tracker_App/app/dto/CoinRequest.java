package com.example.Crypto_Tracker_App.app.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CoinRequest {
    public String coinName;
}
