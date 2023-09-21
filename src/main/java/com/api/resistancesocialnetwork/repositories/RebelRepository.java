package com.api.resistancesocialnetwork.repositories;

import com.api.resistancesocialnetwork.model.Rebel;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface RebelRepository {
    void save(Rebel entity);

    Optional<Rebel> findById(Integer sourceId);

    boolean existsById(Integer mustProvideSourceRebelId);

    List<Rebel> findAll();
}
