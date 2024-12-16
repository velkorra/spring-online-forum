package org.example.onlineforum.services;

import org.example.onlineforum.entities.User;
import org.example.onlineforum.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class ForumUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public ForumUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String emailOrUsername) throws UsernameNotFoundException {
        User user = emailOrUsername.contains("@")
                ? userRepository.findByEmail(emailOrUsername).orElseThrow(() -> new UsernameNotFoundException("User not found"))
                : userRepository.findByUsername(emailOrUsername).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }
}
