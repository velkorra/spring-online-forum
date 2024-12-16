package org.example.onlineforum.dto;

public record NewCommentDto(
        String content,
        String authorUsername,
        String threadId
) {
}
