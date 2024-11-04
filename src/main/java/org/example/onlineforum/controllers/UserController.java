package org.example.onlineforum.controllers;

import org.example.onlineforum.dto.UserDto;
import org.example.onlineforum.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<UserDto> getUsers(){
        return userRepository.findAll().stream().map(UserDto::new).toList();
    }
}
