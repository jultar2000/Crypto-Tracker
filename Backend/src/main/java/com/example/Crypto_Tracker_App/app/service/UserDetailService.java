package com.example.Crypto_Tracker_App.app.service;

import com.example.Crypto_Tracker_App.app.entity.User;
import com.example.Crypto_Tracker_App.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("No such user:" + username));
        return new org.springframework.security.core.userdetails.User
                (user.getUsername(), user.getPassword(),
                        user.isEnabled(), true, true,
                        true, user.getRole().getGrantedAuthorities());
    }
}
