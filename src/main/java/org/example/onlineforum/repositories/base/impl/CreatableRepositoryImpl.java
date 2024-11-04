package org.example.onlineforum.repositories.base.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.onlineforum.repositories.base.CreatableRepository;

public class CreatableRepositoryImpl<T, ID> implements CreatableRepository<T, ID> {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public T create(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public <E extends Iterable<T>> E createAll(E entities) {
        entities.forEach(entityManager::persist);
        return entities;
    }
}
