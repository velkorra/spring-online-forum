package org.example.onlineforum.repositories;

import org.example.onlineforum.entities.ThreadComment;
import org.example.onlineforum.projections.ThreadCommentProjection;
import org.example.onlineforum.repositories.base.MutableRepository;
import org.example.onlineforum.repositories.base.ReadableRepository;
import org.forum.forumcontracts.filters.ThreadCommentFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface ThreadCommentRepository extends
        Repository<ThreadComment, String>,
        MutableRepository<ThreadComment, String>,
        ReadableRepository<ThreadComment, String> {
}
