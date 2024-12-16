package org.example.onlineforum.repositories;

import org.example.onlineforum.entities.Category;
import org.example.onlineforum.repositories.base.DeletableRepository;
import org.example.onlineforum.repositories.base.MutableRepository;
import org.example.onlineforum.repositories.base.ReadableRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;


public interface CategoryRepository extends
        Repository<Category, String>,
        MutableRepository<Category, String>,
        ReadableRepository<Category, String>,
        DeletableRepository<Category, String> {

    @Query("select c from Category c where c.name = ?1")
    Optional<Category> findByName(String name);

    boolean existsByName(String name);
}
