package org.example.onlineforum.services;

import org.example.onlineforum.dto.CategoryCreateDto;
import org.example.onlineforum.dto.CategoryDto;
import org.example.onlineforum.entities.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();
    CategoryDto findByName(String name);
    void reassignThreadsToAnotherCategory(String name, String newCategory);
    void deleteCategoryIfEmpty(String categoryName);
    void createCategory(CategoryCreateDto category);
    void updateName(CategoryDto categoryDto);
}
