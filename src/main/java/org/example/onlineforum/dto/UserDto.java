package org.example.onlineforum.dto;

import org.example.onlineforum.entities.User;
import org.example.onlineforum.entities.enums.Role;

import java.time.LocalDateTime;

public class UserDto {
    public String username;
    public String email;
    public Role role;
    public LocalDateTime createdOn;

    public UserDto(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.createdOn = user.getCreatedOn();
    }
}
