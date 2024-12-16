package org.example.onlineforum.dto;

public record UserRegistrationDto(
        String username,
        String email,
        String password,
        String displayName
) {
}
