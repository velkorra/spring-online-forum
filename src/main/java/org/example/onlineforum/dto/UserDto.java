package org.example.onlineforum.dto;

import java.time.LocalDateTime;

public record UserDto(
        String id,
        String username,
        String displayName,
        String email,
        String role,
        LocalDateTime createdOn) {
}
