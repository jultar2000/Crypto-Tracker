package com.example.Crypto_Tracker_App.app.controller;

import com.example.Crypto_Tracker_App.app.dto.GetUsersResponse;
import com.example.Crypto_Tracker_App.app.entity.User;
import com.example.Crypto_Tracker_App.app.service.CryptoCoinService;
import com.example.Crypto_Tracker_App.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/management")
public class ManagementController {

    private final CryptoCoinService cryptoCoinService;

    private final UserService userService;

    @Autowired
    public ManagementController(CryptoCoinService cryptoCoinService, UserService userService) {
        this.cryptoCoinService = cryptoCoinService;
        this.userService = userService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('manage:readUser')")
    public ResponseEntity<GetUsersResponse> getAllUsers() {
        List<User> all = userService.findAll();
        GetUsersResponse response = GetUsersResponse.entityToDtoMapper(all);
        return ResponseEntity.ok(response);
    }


    @PutMapping
    @PreAuthorize("hasAuthority('db:update')")
    public ResponseEntity<Void> updateDB() throws JSONException, IOException {
        cryptoCoinService.updateDatabase();
        return ResponseEntity.accepted().build();
    }
}
