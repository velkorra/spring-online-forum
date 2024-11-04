package org.example.onlineforum.repositories.base.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.onlineforum.repositories.base.DeletableRepository;

public class DeletableRepositoryImpl<T, ID> implements DeletableRepository<T, ID> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void delete(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public <E extends Iterable<T>> void deleteAll(E entities) {
        entities.forEach(entityManager::remove);
    }
}
