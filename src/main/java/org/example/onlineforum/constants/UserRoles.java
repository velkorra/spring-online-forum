package org.example.onlineforum.constants;

public enum UserRoles {
    USER("USER"),
    ADMIN("ADMIN"),
    MODERATOR("MODERATOR");

    private final String name;

    UserRoles(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}