package org.example.onlineforum.dto;


public record ThreadUpdateDto(
        String id,
        String title,
        String content,
        String category
) {
}
