package org.example.onlineforum.dto.mappers.impl;

import org.example.onlineforum.dto.UserCreateDto;
import org.example.onlineforum.dto.UserDto;
import org.example.onlineforum.dto.UserRegistrationDto;
import org.example.onlineforum.dto.mappers.UserMapper;
import org.example.onlineforum.entities.User;
import org.example.onlineforum.projections.UserProjection;
import org.example.onlineforum.utils.DateConverter;
import org.forum.forumcontracts.input.UserCreateForm;
import org.forum.forumcontracts.input.UserRegistrationForm;
import org.forum.forumcontracts.input.UserUpdateForm;
import org.forum.forumcontracts.viewmodels.FullUserViewModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {
    public FullUserViewModel toViewModel(UserProjection user) {
        return new FullUserViewModel(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getDisplayName(),
                DateConverter.dateFromLocalDateTIme(user.getRegisteredOn()),
                user.getThreadCount(),
                user.getCommentCount(),
                user.getReactionCount()
        );
    }

    @Override
    public UserUpdateForm toUpdateForm(UserDto user) {
        return new UserUpdateForm(
                user.id(),
                user.email(),
                user.username(),
                user.role(),
                user.displayName()
        );
    }

    @Override
    public UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getDisplayName(),
                user.getEmail(),
                user.getRole().toString(),
                user.getCreatedOn()
        );
    }

    @Override
    public UserCreateDto toDto(UserCreateForm user) {
        return new UserCreateDto(user.username(), user.email(), user.password(), user.displayName(), user.role());
    }

    @Override
    public UserRegistrationDto toDto(UserRegistrationForm user) {
        return new UserRegistrationDto(user.username(), user.email(), user.password(), user.displayName());
    }
}
