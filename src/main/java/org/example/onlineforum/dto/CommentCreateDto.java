package org.example.onlineforum.dto;

import org.forum.forumcontracts.input.ThreadCommentCreateForm;

public record CommentCreateDto(
        String content,
        String authorId,
        String threadId,
        String parentCommentId
) {
    public CommentCreateDto(ThreadCommentCreateForm form) {
        this(form.content(), form.authorId(), form.threadId(), form.parentCommentId());
    }
}
