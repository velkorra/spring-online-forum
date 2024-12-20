package org.example.onlineforum.repositories;

import org.example.onlineforum.entities.User;
import org.example.onlineforum.repositories.base.MutableRepository;
import org.example.onlineforum.repositories.base.PageableRepository;
import org.example.onlineforum.repositories.base.ReadableRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends
        Repository<User, String>,
        MutableRepository<User, String>,
        ReadableRepository<User, String>,
        PageableRepository<User, String> {

    @Query("select u from User u where u.email = ?1")
    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.displayName = ?1")
    List<User> findByDisplayName(String name);

    @Query("select u from User u where u.username = ?1")
    Optional<User> findByUsername(String username);

    @Query("select u from User u where u.deleted = true")
    List<User> findAllDeleted();

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);


}
