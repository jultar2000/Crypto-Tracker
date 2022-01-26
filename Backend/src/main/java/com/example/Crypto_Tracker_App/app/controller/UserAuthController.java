package com.example.Crypto_Tracker_App.app.controller;

import com.example.Crypto_Tracker_App.app.dto.AuthUserResponse;
import com.example.Crypto_Tracker_App.app.dto.LoginUserRequest;
import com.example.Crypto_Tracker_App.app.dto.RefreshTokenRequest;
import com.example.Crypto_Tracker_App.app.dto.RegisterUserRequest;
import com.example.Crypto_Tracker_App.app.service.RefreshTokenService;
import com.example.Crypto_Tracker_App.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class UserAuthController {

    private final UserService userService;

    private final RefreshTokenService refreshTokenService;

    @Autowired
    public UserAuthController(UserService userService, RefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterUserRequest registerUserRequest) {
        userService.signup(RegisterUserRequest.dtoToEntityMapper(registerUserRequest));
        return ResponseEntity.ok("Registration successful");
    }

    @GetMapping("/verification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable("token") String token) {
        userService.verifyAccount(token);
        return ResponseEntity.ok("Account Verified");
    }

    @PostMapping("/login")
    public AuthUserResponse login(@RequestBody LoginUserRequest request) {
        return userService.login(request);
    }

    @PostMapping("refresh/token")
    public AuthUserResponse refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return userService.refreshToken(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest request) {
        refreshTokenService.deleteRefreshToken(request.getRefreshToken());
        return ResponseEntity.ok("Logout successful.");
    }
}