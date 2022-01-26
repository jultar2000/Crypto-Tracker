package com.example.Crypto_Tracker_App.app.dto;

import lombok.*;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CoinsRequest {
    List<String> names;
}
