package com.example.Crypto_Tracker_App.app.service;

import com.example.Crypto_Tracker_App.app.dto.AuthUserResponse;
import com.example.Crypto_Tracker_App.app.dto.LoginUserRequest;
import com.example.Crypto_Tracker_App.app.entity.VerificationEmail;
import com.example.Crypto_Tracker_App.app.entity.User;
import com.example.Crypto_Tracker_App.app.entity.VerificationToken;
import com.example.Crypto_Tracker_App.app.exceptions.AppException;
import com.example.Crypto_Tracker_App.app.repository.UserRepository;
import com.example.Crypto_Tracker_App.app.repository.VerificationTokenRepository;
import com.example.Crypto_Tracker_App.app.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final VerificationTokenRepository verificationTokenRepository;

    private final MailService mailService;

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, VerificationTokenRepository verificationTokenRepository, MailService mailService, AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenRepository = verificationTokenRepository;
        this.mailService = mailService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public void signup(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new AppException("Username" + user.getUsername() + "already taken!");
        } else {
            userRepository.save(user);
            String token = generateVerificationToken(user);
            mailService.sendMail(new VerificationEmail("Activate acccount", user.getEmail(),
                    "http://localhost:8084/api/auth/verification/" + token));
        }
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

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new AppException("Invalid Token"));
        registerUser(verificationToken.get());
    }

    @Transactional
    public void registerUser(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("User: " + username + " not found."));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public AuthUserResponse login(LoginUserRequest loginUserRequest) {
        Authentication authenticate =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                        (loginUserRequest.getUsername(), loginUserRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return AuthUserResponse.builder()
                .authenticationToken(token)
                .expiresAt(Instant.now().plusSeconds(jwtProvider.getExpirationTime()))
                .username(loginUserRequest.getUsername())
                .build();
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
