package org.example.onlineforum.repositories.base;

import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReadableRepository<T, ID> {

    @Query("SELECT e FROM #{#entityName} e")
    List<T> findAll();

    @Query("SELECT e FROM #{#entityName} e WHERE e.id = ?1")
    Optional<T> findById(ID id);
}
