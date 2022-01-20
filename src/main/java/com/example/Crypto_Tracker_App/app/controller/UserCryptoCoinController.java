package com.example.Crypto_Tracker_App.app.controller;

import com.example.Crypto_Tracker_App.app.dto.CoinRequest;
import com.example.Crypto_Tracker_App.app.entity.CryptoCoin;
import com.example.Crypto_Tracker_App.app.entity.User;
import com.example.Crypto_Tracker_App.app.exceptions.AppException;
import com.example.Crypto_Tracker_App.app.service.CryptoCoinService;
import com.example.Crypto_Tracker_App.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/coins/user")
public class UserCryptoCoinController {

    private final CryptoCoinService cryptoCoinService;

    private final UserService userService;

    @Autowired
    public UserCryptoCoinController(CryptoCoinService cryptoCoinService, UserService userService) {
        this.cryptoCoinService = cryptoCoinService;
        this.userService = userService;
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<String> getUserCoins() throws IOException {
        User user = userService.getCurrentUser();
        List<String> stringList = new ArrayList<>();
        user.getCryptoCoins().forEach(cryptoCoin ->
                stringList.add(cryptoCoin.getCoinName()));
        String coins = cryptoCoinService.getSpecificCoins(stringList);
        return ResponseEntity.ok(coins);
    }

    @PutMapping("")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<Void> addCoin(@RequestBody CoinRequest request) {
        User user = userService.getCurrentUser();
        Optional<CryptoCoin> coin = cryptoCoinService.findCoin(request.coinName);
        if (coin.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (user.getCryptoCoins().contains(coin.get())) {
            throw new AppException("Current user already tracks " + coin.get().getCoinName() + ".");
        }
        user.getCryptoCoins().add(coin.get());
        userService.save(user);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<Void> deleteCoin(@RequestBody CoinRequest request) {
        User user = userService.getCurrentUser();
        Optional<CryptoCoin> coin = cryptoCoinService.findCoin(request.coinName);
        if (coin.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (user.getCryptoCoins().contains(coin.get())) {
            throw new AppException("Current user doesn't track" + coin.get().getCoinName() + ".");
        }
        user.getCryptoCoins().remove(coin.get());
        userService.save(user);
        return ResponseEntity.accepted().build();
    }
}
