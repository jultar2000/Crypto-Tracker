package com.example.Crypto_Tracker_App.app.controller;

import com.example.Crypto_Tracker_App.app.service.CryptoCoinService;
import com.example.Crypto_Tracker_App.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/{username}/crypto")
public class UserCryptoCoinController {

    private final UserService userService;

    private CryptoCoinService cryptoCoinService;

    @Autowired
    public UserCryptoCoinController(UserService userService, CryptoCoinService cryptoCoinService) {
        this.userService = userService;
        this.cryptoCoinService = cryptoCoinService;
    }

//    @GetMapping
//    public ResponseEntity<String> getCoin(@PathVariable("username") String username) {
//
//        return ResponseEntity.ok(sb.toString());
//    }
}