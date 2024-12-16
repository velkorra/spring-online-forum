package org.example.onlineforum.dto;

import org.example.onlineforum.entities.ThreadComment;

import java.time.LocalDateTime;

public class ThreadCommentDto {
    public String id;
    public String authorId;
    public String content;
    public LocalDateTime createdAt;

    public ThreadCommentDto(ThreadComment comment){
        this.id = comment.getId();
        this.authorId = comment.getAuthor().getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }
}
