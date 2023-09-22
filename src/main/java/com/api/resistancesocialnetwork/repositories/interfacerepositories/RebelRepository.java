package com.api.resistancesocialnetwork.repositories.interfacerepositories;

import com.api.resistancesocialnetwork.model.Rebel;

import java.util.List;
import java.util.Optional;

public interface RebelRepository {
    void save(Rebel rebel);

    Optional<Rebel> findById(Integer id);

    boolean existsById(Integer id);

    List<Rebel> findAll();
}
