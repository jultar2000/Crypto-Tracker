package com.example.Crypto_Tracker_App.app.controller;

import com.example.Crypto_Tracker_App.app.dto.GetCoinsRequest;
import com.example.Crypto_Tracker_App.app.service.CryptoCoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/crypto")
public class CryptoCoinController {

    private CryptoCoinService cryptoCoinService;

    @Autowired
    public CryptoCoinController(CryptoCoinService cryptoCoinService) {
        this.cryptoCoinService = cryptoCoinService;
    }

    @GetMapping
    public ResponseEntity<String> getCoins(@RequestBody GetCoinsRequest request) throws IOException {
        String coins = cryptoCoinService.getCoins(request);
        return ResponseEntity.ok(coins);
    }
}
