package org.example.onlineforum.services.implementations;

import jakarta.transaction.Transactional;
import org.example.onlineforum.dto.CategoryCreateDto;
import org.example.onlineforum.dto.CategoryDto;
import org.example.onlineforum.entities.Category;
import org.example.onlineforum.entities.ForumThread;
import org.example.onlineforum.exceptions.CategoryExistsException;
import org.example.onlineforum.exceptions.CategoryNotFoundException;
import org.example.onlineforum.repositories.CategoryRepository;
import org.example.onlineforum.repositories.ForumThreadRepository;
import org.example.onlineforum.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final ForumThreadRepository forumThreadRepository;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(ForumThreadRepository forumThreadRepository, CategoryRepository categoryRepository) {
        this.forumThreadRepository = forumThreadRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryDto findByName(String name) {
        return new CategoryDto(categoryRepository.findByName(name).orElseThrow(
                () -> new CategoryNotFoundException("Category \"" + name + "\" does not exist ")
        ));
    }

    @Override
    @Transactional
    public void reassignThreadsToAnotherCategory(String fromCategory, String toCategory) {
        Category sourceCategory = categoryRepository.findByName(fromCategory).orElseThrow(
                () -> new CategoryNotFoundException("Category \"" + fromCategory + "\" does not exist.")
        );
        Category targetCategory = categoryRepository.findByName(toCategory).orElseThrow(
                () -> new CategoryNotFoundException("Category \"" + toCategory + "\" does not exist.")
        );

        List<ForumThread> threadsToReassign = forumThreadRepository.getAllByCategory(sourceCategory);
        for (ForumThread thread : threadsToReassign) {
            thread.setCategory(targetCategory);
        }

        forumThreadRepository.updateAll(threadsToReassign);
    }

    @Override
    @Transactional
    public void deleteCategoryIfEmpty(String categoryName) {
        Category category = categoryRepository.findByName(categoryName).orElseThrow(
                () -> new CategoryNotFoundException("Category \"" + categoryName + "\" does not exist.")
        );

        int threadCount = forumThreadRepository.countByCategory(category);
        if (threadCount > 0) {
            throw new IllegalStateException("Cannot delete category \"" + categoryName + "\" because it still contains threads.");
        }

        categoryRepository.delete(category);
    }

    @Override
    @Transactional
    public void createCategory(CategoryCreateDto category) {
        if (categoryRepository.existsByName(category.name())) {
            throw new CategoryExistsException("Category \"" + category.name() + "\" already exists");
        }
        categoryRepository.create(new Category(category.name()));
    }

    @Override
    @Transactional
    public void updateName(CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryDto.id()).orElseThrow(
                () -> new CategoryNotFoundException("Category with id " + categoryDto.id() + " does not exists")
        );
        category.setName(categoryDto.name());
        categoryRepository.update(category);
    }
}
