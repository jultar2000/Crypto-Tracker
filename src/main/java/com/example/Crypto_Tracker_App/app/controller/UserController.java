package com.example.Crypto_Tracker_App.app.controller;

import com.example.Crypto_Tracker_App.app.dto.RegisterUserRequest;
import com.example.Crypto_Tracker_App.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterUserRequest registerUserRequest){
        userService.signup(RegisterUserRequest.dtoToEntityMapper(registerUserRequest));
        return ResponseEntity.ok("Registration successful");
    }
}
