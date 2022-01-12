package com.example.Crypto_Tracker_App.app.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "crypto_coin")
public class CryptoCoin {
    @Id
    private String coinName;
}
