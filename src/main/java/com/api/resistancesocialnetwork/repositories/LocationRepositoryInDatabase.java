package com.api.resistancesocialnetwork.repositories;

import com.api.resistancesocialnetwork.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Component
public class LocationRepositoryInDatabase implements LocationRepository {
    private final LocationRepositoryJpa adapter;

    public LocationRepositoryInDatabase(LocationRepositoryJpa adapter) {
        this.adapter = adapter;
    }

    @Override
    public void save(Location location) {
        adapter.save(location);
    }

    @Override
    public Optional<Location> findById(Integer mustProvideLocationId) {
        return adapter.findById(mustProvideLocationId);
    }
}

@Repository
interface LocationRepositoryJpa extends JpaRepository<Location, Integer> {
}
