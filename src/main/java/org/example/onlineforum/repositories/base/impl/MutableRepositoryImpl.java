package org.example.onlineforum.repositories.base.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.onlineforum.repositories.base.MutableRepository;

public class MutableRepositoryImpl<T, ID> extends CreatableRepositoryImpl<T, ID> implements MutableRepository<T, ID> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public <E extends Iterable<T>> E updateAll(E entities) {
        entities.forEach(entityManager::merge);
        return entities;
    }
}
