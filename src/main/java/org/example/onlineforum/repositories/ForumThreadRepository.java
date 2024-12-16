package org.example.onlineforum.repositories;

import org.example.onlineforum.entities.Category;
import org.example.onlineforum.entities.ForumThread;
import org.example.onlineforum.repositories.base.MutableRepository;
import org.example.onlineforum.repositories.base.ReadableRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ForumThreadRepository extends
        Repository<ForumThread, String>,
        MutableRepository<ForumThread, String>,
        ReadableRepository<ForumThread, String> {

    @Query("select t from ForumThread t where t.category = ?1")
    List<ForumThread> getAllByCategory(Category category);

    int countByCategory(Category category);
}
