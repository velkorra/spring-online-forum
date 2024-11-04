package org.example.onlineforum.repositories;

import org.example.onlineforum.entities.PinnedThreads;
import org.example.onlineforum.repositories.base.CreatableRepository;
import org.example.onlineforum.repositories.base.DeletableRepository;
import org.springframework.data.repository.Repository;

public interface PinnedThreadsRepository extends
        Repository<PinnedThreads, String>,
        CreatableRepository<PinnedThreads, String>,
        DeletableRepository<PinnedThreads, String> {


}
