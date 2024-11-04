package org.example.onlineforum.repositories;

import org.example.onlineforum.entities.Category;
import org.example.onlineforum.repositories.base.MutableRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;


public interface CategoryRepository extends
        Repository<Category, String>,
        MutableRepository<Category, String> {

    @Query("select c from Category c where c.name = ?1")
    Category findByName(String name);
}
