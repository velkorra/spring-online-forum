package org.example.onlineforum.dto;

import org.forum.forumcontracts.input.ThreadCommentUpdateForm;

public record CommentUpdateDto(
        String id,
        String content
) {
    public CommentUpdateDto(ThreadCommentUpdateForm form) {
        this(form.id(), form.content());
    }
}
