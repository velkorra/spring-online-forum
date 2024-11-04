package org.example.onlineforum.repositories;

import org.example.onlineforum.entities.ThreadComment;
import org.example.onlineforum.repositories.base.MutableRepository;
import org.springframework.data.repository.Repository;

public interface ThreadCommentRepository extends
        Repository<ThreadComment, String>,
        MutableRepository<ThreadComment, String> {
}
