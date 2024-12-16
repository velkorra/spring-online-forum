package org.example.onlineforum.services.implementations;

import jakarta.transaction.Transactional;
import org.example.onlineforum.constants.UserRoles;
import org.example.onlineforum.dto.UserRegistrationDto;
import org.example.onlineforum.entities.User;
import org.example.onlineforum.exceptions.EmailAlreadyExistsException;
import org.example.onlineforum.exceptions.UsernameAlreadyExistsException;
import org.example.onlineforum.repositories.UserRepository;
import org.example.onlineforum.services.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void register(UserRegistrationDto user) {
        if (userRepository.existsByEmail(user.email())) {
            throw new EmailAlreadyExistsException(user.email());
        }
        if (userRepository.existsByUsername(user.username())) {
            throw new UsernameAlreadyExistsException(user.username());
        }
        User newUser = new User(
                user.username(),
                passwordEncoder.encode(user.password()),
                user.email(),
                user.displayName() != null && user.displayName().isBlank() ? user.displayName() : user.username(),
                UserRoles.USER
        );
        userRepository.create(newUser);
    }
}
