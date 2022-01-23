package com.example.Crypto_Tracker_App.app.controller;

import com.example.Crypto_Tracker_App.app.dto.CoinsRequest;
import com.example.Crypto_Tracker_App.app.service.CryptoCoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/coins")
public class CryptoCoinController {

    private final CryptoCoinService cryptoCoinService;

    @Autowired
    public CryptoCoinController(CryptoCoinService cryptoCoinService) {
        this.cryptoCoinService = cryptoCoinService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<String> getAllCoins() throws IOException {
        String coins = cryptoCoinService.getAllCoins();
        return ResponseEntity.ok(coins);
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<String> getSpecificCoins(@RequestBody CoinsRequest request) throws IOException {
        String coins = cryptoCoinService.getSpecificCoins(request.getNames());
        return ResponseEntity.ok(coins);
    }
}
