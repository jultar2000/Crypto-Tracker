package com.example.Crypto_Tracker_App.user.service;

import com.example.Crypto_Tracker_App.user.entity.User;
import com.example.Crypto_Tracker_App.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void save(User user){
        userRepository.save(user);
    }

    @Transactional
    public void delete(User user){
        userRepository.delete(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public Optional<User> findUser(Long userID) {
        return userRepository.findById(userID);
    }
}
