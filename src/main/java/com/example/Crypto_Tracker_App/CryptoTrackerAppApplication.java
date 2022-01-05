package com.example.Crypto_Tracker_App;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CryptoTrackerAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptoTrackerAppApplication.class, args);
	}

}
