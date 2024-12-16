package org.example.onlineforum.dto;

import org.forum.forumcontracts.input.ThreadCreateFrom;

public record ThreadCreateDro(
        String authorId,
        String title,
        String content,
        String category
) {
    public ThreadCreateDro(ThreadCreateFrom form){
        this(form.authorId(), form.title(), form.content(), form.category());
    }
}
