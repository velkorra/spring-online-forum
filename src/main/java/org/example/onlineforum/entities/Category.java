package org.example.onlineforum.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    private String name;
    private Set<ForumThread> threads;

    protected Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "category")
    public Set<ForumThread> getThreads() {
        return threads;
    }

    public void setThreads(Set<ForumThread> threads) {
        this.threads = threads;
    }
}
