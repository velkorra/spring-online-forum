package org.example.onlineforum.services;

import org.example.onlineforum.dto.*;
import org.example.onlineforum.projections.ThreadCommentProjection;
import org.forum.forumcontracts.filters.ThreadCommentFilter;
import org.forum.forumcontracts.input.NewCommentForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ThreadCommentService {
    void createComment(CommentCreateDto commentCreateDto);
    void newComment(NewCommentDto newCommentDto);

    void updateComment(CommentUpdateDto commentUpdateDto);

    ThreadCommentDto getCommentById(String commentId);

    void checkCommentOwnershipOrThrow(String commentId, String username);

    Page<ThreadCommentProjection> searchComments(ThreadCommentFilter filter, Pageable pageable);

    void markDeleted(String id);



}
