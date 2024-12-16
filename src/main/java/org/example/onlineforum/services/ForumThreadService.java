package org.example.onlineforum.services;

import org.example.onlineforum.dto.NewThreadDto;
import org.example.onlineforum.dto.ThreadCreateDro;
import org.example.onlineforum.dto.ThreadDto;
import org.example.onlineforum.dto.ThreadUpdateDto;
import org.example.onlineforum.projections.ThreadProjection;
import org.example.onlineforum.projections.dto.ThreadProjectionDto;
import org.forum.forumcontracts.filters.ForumThreadFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ForumThreadService {
    ThreadProjection getThreadById(String id);

    List<ThreadDto> getAllThreads();

    Page<ThreadProjectionDto> searchThreads(ForumThreadFilter filter, Pageable pageable);

    void createThread(ThreadCreateDro thread);
    void createThread(String username, NewThreadDto thread);
    void updateThread(ThreadUpdateDto thread);

    void checkThreadOwnershipOrThrow(String threadId, String userId);

    void markDeleted(String id);

}
