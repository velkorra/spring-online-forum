package org.example.onlineforum.repositories.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface PageableRepository<T, ID>{
    @Query("select e from #{#entityName} e")
    Page<T> findAllPaged(Pageable pageable);
}
