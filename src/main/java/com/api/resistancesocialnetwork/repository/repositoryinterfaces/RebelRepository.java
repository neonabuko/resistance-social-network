package com.api.resistancesocialnetwork.repository.repositoryinterfaces;

import com.api.resistancesocialnetwork.entity.Rebel;

import java.util.List;
import java.util.Optional;

public interface RebelRepository {
    void save(Rebel rebel);

    Optional<Rebel> findById(Integer id);

    boolean existsById(Integer id);

    List<Rebel> findAll();

    void saveAll(List<Rebel> rebels);
}