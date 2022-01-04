package com.example.Crypto_Tracker_App.app.service;

import com.example.Crypto_Tracker_App.app.entity.VerificationEmail;
import com.example.Crypto_Tracker_App.app.entity.User;
import com.example.Crypto_Tracker_App.app.entity.VerificationToken;
import com.example.Crypto_Tracker_App.app.repository.UserRepository;
import com.example.Crypto_Tracker_App.app.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final VerificationTokenRepository verificationTokenRepository;

    private final MailService mailService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, VerificationTokenRepository verificationTokenRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenRepository = verificationTokenRepository;
        this.mailService = mailService;
    }

    @Transactional
    public void signup(User user) {
        userRepository.save(user);
        String token = generateVerificationToken(user);
        mailService.sendMail(new VerificationEmail("Activate acccount", user.getEmail(),
                "http//localhost:8084/api/auth/verification/" + token));
    }

    public String generateVerificationToken(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .user(user)
                .build();

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findUser(Long userID) {
        return userRepository.findById(userID);
    }
}
