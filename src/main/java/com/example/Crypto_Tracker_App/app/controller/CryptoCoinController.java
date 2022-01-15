package com.example.Crypto_Tracker_App.app.controller;

import com.example.Crypto_Tracker_App.app.dto.CoinsRequest;
import com.example.Crypto_Tracker_App.app.entity.CryptoCoin;
import com.example.Crypto_Tracker_App.app.entity.User;
import com.example.Crypto_Tracker_App.app.service.CryptoCoinService;
import com.example.Crypto_Tracker_App.app.service.UserService;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/coins")
public class CryptoCoinController {

    private final CryptoCoinService cryptoCoinService;

    private final UserService userService;

    @Autowired
    public CryptoCoinController(CryptoCoinService cryptoCoinService, UserService userService) {
        this.cryptoCoinService = cryptoCoinService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<String> getAllCoins() throws IOException {
        String coins = cryptoCoinService.getAllCoins();
        return ResponseEntity.ok(coins);
    }

    @GetMapping("")
    public ResponseEntity<String> getSpecificCoins(@RequestBody CoinsRequest request) throws IOException {
        String coins = cryptoCoinService.getSpecificCoins(request.getNames());
        return ResponseEntity.ok(coins);
    }

    @GetMapping("/user")
    public ResponseEntity<String> getUserCoins() throws IOException {
        User user = userService.getCurrentUser();
        List<String> stringList = new ArrayList<>();
        user.getCryptoCoins().forEach(cryptoCoin ->
                stringList.add(cryptoCoin.getCoinName()));
        String coins = cryptoCoinService.getSpecificCoins(stringList);
        return ResponseEntity.ok(coins);
    }

    @PutMapping("/user")
    public ResponseEntity<String> addCoins(@RequestBody CoinsRequest request) {
        User user = userService.getCurrentUser();
        List<CryptoCoin> coinList = new ArrayList<>();
        request.getNames().forEach(name -> {
            CryptoCoin coin = CryptoCoin.builder()
                    .coinName(name)
                    .build();
            cryptoCoinService.save(coin);
            coinList.add(coin);
        });
        user.getCryptoCoins().addAll(coinList);
        userService.save(user);
        return ResponseEntity.accepted().build();
    }
}
