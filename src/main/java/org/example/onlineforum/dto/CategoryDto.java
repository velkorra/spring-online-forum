package org.example.onlineforum.dto;

import org.example.onlineforum.entities.Category;

public record CategoryDto(
        String id,
        String name
) {
    public CategoryDto(Category category){
        this(category.getId(), category.getName());
    }
}
