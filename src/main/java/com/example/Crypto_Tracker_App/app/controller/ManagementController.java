package com.example.Crypto_Tracker_App.app.controller;

import com.example.Crypto_Tracker_App.app.service.CryptoCoinService;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/management")
public class ManagementController {

    private final CryptoCoinService cryptoCoinService;

    public ManagementController(CryptoCoinService cryptoCoinService) {
        this.cryptoCoinService = cryptoCoinService;
    }

    @PutMapping
    @PreAuthorize("hasAuthority('db:update')")
    public ResponseEntity<Void> updateDB() throws JSONException, IOException {
        cryptoCoinService.updateDatabase();
        return ResponseEntity.accepted().build();
    }
}
