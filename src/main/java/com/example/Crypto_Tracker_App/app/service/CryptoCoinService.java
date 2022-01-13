package com.example.Crypto_Tracker_App.app.service;

import com.example.Crypto_Tracker_App.app.dto.GetCoinsRequest;
import com.example.Crypto_Tracker_App.app.entity.CryptoCoin;
import com.example.Crypto_Tracker_App.app.repository.CryptoCoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Service
public class CryptoCoinService {

    private CryptoCoinRepository cryptoCoinRepository;

    @Autowired
    public CryptoCoinService(CryptoCoinRepository cryptoCoinRepository) {
        this.cryptoCoinRepository = cryptoCoinRepository;
    }

    public String getCoins(GetCoinsRequest request) throws IOException {
        String output;
        StringBuilder sb = new StringBuilder();

        for (String coin : request.getNames()) {
            coin = "%2C" + coin;
            sb.append(coin);
        }

        URL url = new URL("https://api.coingecko.com/api/v3/simple/price" +
                "?ids=" +
                request.getNames().get(0) +
                sb +
                "&vs_currencies=usd" +
                "&include_market_cap=true" +
                "&include_24hr_vol=true" +
                "&include_24hr_change=true" +
                "&include_last_updated_at=true");

        sb.setLength(0);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }

        while()


        return sb.toString();
    }

    @Transactional
    public void save(CryptoCoin cryptoCoin) {
        cryptoCoinRepository.save(cryptoCoin);
    }

    @Transactional
    public void delete(CryptoCoin cryptoCoin) {
        cryptoCoinRepository.delete(cryptoCoin);
    }

    public List<CryptoCoin> findAll() {
        return cryptoCoinRepository.findAll();
    }

    public Optional<CryptoCoin> findUser(String coinName) {
        return cryptoCoinRepository.findById(coinName);
    }

}
