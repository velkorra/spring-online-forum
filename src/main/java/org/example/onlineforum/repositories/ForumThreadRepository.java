package org.example.onlineforum.repositories;

import org.example.onlineforum.entities.ForumThread;
import org.example.onlineforum.repositories.base.MutableRepository;
import org.springframework.data.repository.Repository;

public interface ForumThreadRepository extends
        Repository<ForumThread, String>,
        MutableRepository<ForumThread, String> {
}
