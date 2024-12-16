package org.example.onlineforum.services;

import org.example.onlineforum.dto.UserRegistrationDto;

public interface AuthService {
    void register(UserRegistrationDto user);
}
