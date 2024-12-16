package org.example.onlineforum.dto.mappers;

import org.example.onlineforum.dto.UserCreateDto;
import org.example.onlineforum.dto.UserDto;
import org.example.onlineforum.dto.UserRegistrationDto;
import org.example.onlineforum.entities.User;
import org.example.onlineforum.projections.UserProjection;
import org.forum.forumcontracts.input.UserCreateForm;
import org.forum.forumcontracts.input.UserRegistrationForm;
import org.forum.forumcontracts.input.UserUpdateForm;
import org.forum.forumcontracts.viewmodels.FullUserViewModel;

public interface UserMapper {
    FullUserViewModel toViewModel(UserProjection user);
    UserUpdateForm toUpdateForm(UserDto user);
    UserDto toDto(User user);
    UserCreateDto toDto(UserCreateForm user);
    UserRegistrationDto toDto(UserRegistrationForm user);

}
