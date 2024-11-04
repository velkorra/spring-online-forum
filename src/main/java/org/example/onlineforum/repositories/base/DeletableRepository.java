package org.example.onlineforum.repositories.base;

public interface DeletableRepository<T, ID> {
    void delete(T entity);

    <E extends Iterable<T>> void deleteAll(E entities);
}
