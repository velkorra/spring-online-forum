package org.example.onlineforum.entities;

import jakarta.persistence.*;

@MappedSuperclass
public class BaseEntity {
    private String id;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
