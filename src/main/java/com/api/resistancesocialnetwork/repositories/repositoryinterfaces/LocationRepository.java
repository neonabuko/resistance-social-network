package com.api.resistancesocialnetwork.repositories.repositoryinterfaces;

import com.api.resistancesocialnetwork.model.Location;

import java.util.List;
import java.util.Optional;

public interface LocationRepository {
    void save(Location location);

    Optional<Location> findById(Integer id);

    boolean existsById(Integer id);

    List<Location> findAll();
}
