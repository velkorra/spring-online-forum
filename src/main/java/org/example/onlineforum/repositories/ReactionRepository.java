package org.example.onlineforum.repositories;

import org.example.onlineforum.entities.Reaction;
import org.example.onlineforum.repositories.base.CreatableRepository;
import org.example.onlineforum.repositories.base.DeletableRepository;
import org.springframework.data.repository.Repository;

public interface ReactionRepository extends
        Repository<Reaction, String>,
        CreatableRepository<Reaction, String>,
        DeletableRepository<Reaction, String> {

}
