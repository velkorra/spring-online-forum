package org.example.onlineforum.exceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String categoryName) {
        super("Category with name " + categoryName + " not found");

    }
}
