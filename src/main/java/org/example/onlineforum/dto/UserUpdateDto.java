package org.example.onlineforum.dto;

import org.example.onlineforum.constants.UserRoles;
import org.forum.forumcontracts.input.UserUpdateForm;

public record UserUpdateDto(
        String id,
        String username,
        String email,
        String displayName,
        UserRoles role
) {
    public UserUpdateDto(UserUpdateForm user){
        this(user.id(), user.username(), user.email(), user.displayName(), org.example.onlineforum.constants.UserRoles.valueOf(user.role().toUpperCase()));
    }
}
