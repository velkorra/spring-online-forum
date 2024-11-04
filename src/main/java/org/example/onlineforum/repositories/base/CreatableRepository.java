package org.example.onlineforum.repositories.base;

public interface CreatableRepository<T, ID> {
    T create(T entity);

    <E extends Iterable<T>> E createAll(E entities);

}
