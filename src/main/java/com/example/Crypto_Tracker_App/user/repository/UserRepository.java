package com.example.Crypto_Tracker_App.user.repository;

import com.example.Crypto_Tracker_App.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
