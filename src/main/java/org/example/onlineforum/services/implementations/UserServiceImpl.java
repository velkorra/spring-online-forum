package org.example.onlineforum.services.implementations;

import jakarta.transaction.Transactional;
import org.example.onlineforum.config.RestPage;
import org.example.onlineforum.constants.UserRoles;
import org.example.onlineforum.dto.UserDto;
import org.example.onlineforum.dto.UserCreateDto;
import org.example.onlineforum.dto.UserRegistrationDto;
import org.example.onlineforum.dto.UserUpdateDto;
import org.example.onlineforum.dto.mappers.UserMapper;
import org.example.onlineforum.entities.User;
import org.example.onlineforum.exceptions.EmailAlreadyExistsException;
import org.example.onlineforum.exceptions.UserNotFoundException;
import org.example.onlineforum.exceptions.UsernameAlreadyExistsException;
import org.example.onlineforum.projections.dto.UserProjectionDto;
import org.example.onlineforum.repositories.search.UserSearchRepository;
import org.forum.forumcontracts.filters.UserFilter;
import org.example.onlineforum.projections.UserProjection;
import org.example.onlineforum.repositories.UserRepository;
import org.example.onlineforum.services.UserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableCaching
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserSearchRepository userSearchRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserSearchRepository userSearchRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userSearchRepository = userSearchRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto getUserById(String id) {
        return userMapper.toDto(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found")));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toDto).toList();
    }

    @Override
    public Page<UserDto> getPage(Pageable pageable) {
        return userRepository.findAllPaged(pageable).map(userMapper::toDto);
    }

    @Override
    @Transactional
    public void createUser(UserCreateDto user) {
        userRepository.create(new User(user.username(), user.password(), user.email(), user.displayName(), UserRoles.valueOf(user.role().toUpperCase())));
    }




    @Transactional
    public void updateUser(UserUpdateDto user) {
        User existingUser = userRepository.findById(user.id())
                .orElseThrow(() -> new UserNotFoundException("User with id " + user.id() + " not found"));
        existingUser.setEmail(user.email());
        existingUser.setUsername(user.username());
        existingUser.setRole(user.role());
        existingUser.setDisplayName(user.displayName());
        userRepository.update(existingUser);

    }

    @Override
    @Cacheable("users")
    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found"));
        return userMapper.toDto(user);
    }

    @Override
    @Cacheable("users")
    public List<UserDto> getUsersByDisplayName(String name) {
        return userRepository.findByDisplayName(name).stream().map(userMapper::toDto).toList();
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
        return userMapper.toDto(user);
    }

    @Override
    @Cacheable("users")
    public Page<UserProjectionDto> getUsersWithCounts(UserFilter userFilter, Pageable pageable) {
        return new RestPage<>(userSearchRepository.searchUsers(userFilter, pageable).map(UserProjectionDto::new));
    }

    @Override
    @Transactional
    public void markDeleted(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        user.setDeleted(true);
        userRepository.update(user);
    }

    @Override
    public void restore(String id) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        if (user.getDeleted()) {
            user.setDeleted(false);
        } else {
            throw new UserNotFoundException("User is not deleted");
        }
    }

}
