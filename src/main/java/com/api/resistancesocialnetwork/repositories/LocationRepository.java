package com.api.resistancesocialnetwork.repositories;

import com.api.resistancesocialnetwork.model.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface LocationRepository {
    void save(Location location);

    Optional<Location> findById(Integer mustProvideLocationId);
}
