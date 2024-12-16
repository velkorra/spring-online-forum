package org.example.onlineforum.dto;

import org.forum.forumcontracts.input.UserCreateForm;

public record UserCreateDto(
        String username,
        String email,
        String password,
        String displayName,
        String role
) {
}
