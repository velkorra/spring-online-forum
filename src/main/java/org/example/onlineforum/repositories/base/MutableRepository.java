package org.example.onlineforum.repositories.base;


public interface MutableRepository <T, ID> extends CreatableRepository<T, ID>{
    T update(T entity);
    <E extends Iterable<T>> E updateAll(E entities);
}
