package org.example.onlineforum.dto;

import org.forum.forumcontracts.input.NewThreadForm;

public record NewThreadDto(
        String title,
        String content,
        String category
) {
}
