package com.example.Crypto_Tracker_App.app.service;

import com.example.Crypto_Tracker_App.app.entity.CryptoCoin;
import com.example.Crypto_Tracker_App.app.exceptions.AppException;
import com.example.Crypto_Tracker_App.app.repository.CryptoCoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
public class CryptoCoinService {

    private final CryptoCoinRepository cryptoCoinRepository;

    @Autowired
    public CryptoCoinService(CryptoCoinRepository cryptoCoinRepository) {
        this.cryptoCoinRepository = cryptoCoinRepository;
    }

    public String getSpecificCoins(List<String> coinNames) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (coinNames.isEmpty()) {
            throw new AppException("List of requested coins is empty!");
        }
        for (String coin : coinNames) {
            coin = "%2C" + coin;
            sb.append(coin);
        }
        URL url = new URL("https://api.coingecko.com/api/v3/simple/price" +
                "?ids=" +
                coinNames.get(0) +
                sb +
                "&vs_currencies=usd" +
                "&include_market_cap=true" +
                "&include_24hr_vol=true" +
                "&include_24hr_change=true" +
                "&include_last_updated_at=true");
        sb.setLength(0);
        BufferedReader br = sendRequest(url);
        return readOutput(sb, br);
    }

    public String getAllCoins() throws IOException {
        StringBuilder sb = new StringBuilder();
        URL url = new URL("https://api.coingecko.com/api/v3/coins/markets?" +
                "vs_currency=usd" +
                "&order=market_cap_desc" +
                "&per_page=100" +
                "&page=1" +
                "&sparkline=false");
        BufferedReader br = sendRequest(url);
        return readOutput(sb, br);
    }

    private BufferedReader sendRequest(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        return new BufferedReader(new InputStreamReader(con.getInputStream()));
    }

    private String readOutput(StringBuilder sb, BufferedReader br) throws IOException {
        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        return sb.toString();
    }

    public void updateDatabase() throws IOException, JSONException {
        JSONArray jsonarray = null;
        String output;
        String coinName;
        URL url = new URL("https://api.coingecko.com/api/v3/coins/markets" +
                "?vs_currency=usd" +
                "&order=market_cap_desc" +
                "&per_page=300&page=1" +
                "&sparkline=false");

        BufferedReader br = sendRequest(url);
        while ((output = br.readLine()) != null) {
            jsonarray = new JSONArray(output);
        }
        br.close();
        if (jsonarray != null) {
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                coinName = jsonobject.getString("id");
                if (!coinExists(coinName)) {
                    saveWithName(coinName);
                }
            }
        }
    }

    @Transactional
    public void saveWithName(String coinName) {
        CryptoCoin coin = CryptoCoin.builder()
                .coinName(coinName)
                .build();
        cryptoCoinRepository.save(coin);
    }

    @Transactional
    public void deleteWithName(String coinName) {
        cryptoCoinRepository.deleteByCoinName(coinName);
    }

    @Transactional
    public void save(CryptoCoin cryptoCoin) {
        cryptoCoinRepository.save(cryptoCoin);
    }

    @Transactional
    public void delete(CryptoCoin cryptoCoin) {
        cryptoCoinRepository.delete(cryptoCoin);
    }

    boolean coinExists(String coinName) {
        return cryptoCoinRepository.existsByCoinName(coinName);
    }

    public Optional<CryptoCoin> findCoin(String coinName) {
        return cryptoCoinRepository.findByCoinName(coinName);
    }

    public List<CryptoCoin> findAll() {
        return cryptoCoinRepository.findAll();
    }

    public Optional<CryptoCoin> findCoin(Long coinId) {
        return cryptoCoinRepository.findById(coinId);
    }
}
