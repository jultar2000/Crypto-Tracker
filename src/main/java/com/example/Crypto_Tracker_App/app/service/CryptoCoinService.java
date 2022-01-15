package com.example.Crypto_Tracker_App.app.service;

import com.example.Crypto_Tracker_App.app.entity.CryptoCoin;
import com.example.Crypto_Tracker_App.app.exceptions.AppException;
import com.example.Crypto_Tracker_App.app.repository.CryptoCoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        if(coinNames.isEmpty()){
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

        return readAndModifyOutput(sb, br);
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

        return readAndModifyOutput(sb, br);
    }

    private BufferedReader sendRequest(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        return new BufferedReader(new InputStreamReader(con.getInputStream()));
    }

    private String readAndModifyOutput(StringBuilder sb, BufferedReader br) throws IOException {
        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }

        Set<Character> values = new HashSet<>(Arrays.asList('{', '}', ','));
        for (int i = 0; i < sb.length(); i++) {
            if (values.contains(sb.charAt(i))) {
                sb.insert(i + 1, System.getProperty("line.separator"));
            }
        }
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

    public Optional<CryptoCoin> findUser(Long coinId) {
        return cryptoCoinRepository.findById(coinId);
    }

}
