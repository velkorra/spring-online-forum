package org.example.onlineforum.services;

import org.example.onlineforum.dto.UserDto;
import org.example.onlineforum.dto.UserCreateDto;
import org.example.onlineforum.dto.UserRegistrationDto;
import org.example.onlineforum.dto.UserUpdateDto;
import org.example.onlineforum.projections.dto.UserProjectionDto;
import org.forum.forumcontracts.filters.UserFilter;
import org.example.onlineforum.projections.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    void createUser(UserCreateDto user);
    void updateUser(UserUpdateDto user);
    UserDto getUserById(String id);
    List<UserDto> getAllUsers();
    Page<UserDto> getPage(Pageable pageable);
    UserDto getUserByUsername(String username);

    List<UserDto> getUsersByDisplayName(String name);

    Page<UserProjectionDto> getUsersWithCounts(UserFilter userFilter, Pageable pageable);

    UserDto getUserByEmail(String email);

    void markDeleted(String id);

    void restore(String id);

}
