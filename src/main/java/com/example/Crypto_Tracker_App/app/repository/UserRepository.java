package com.example.Crypto_Tracker_App.app.repository;

import com.example.Crypto_Tracker_App.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
