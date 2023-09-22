package com.api.resistancesocialnetwork.repositories.repositoriesindatabase;

import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Component
public class LocationRepositoryInDatabase implements LocationRepository {
    private final LocationRepositoryJpa adapter;

    @Autowired
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

    @Override
    public boolean existsById(Integer id) {
        return adapter.existsById(id);
    }

    @Override
    public List<Location> findAll() {
        return adapter.findAll();
    }
}

@Repository
interface LocationRepositoryJpa extends JpaRepository<Location, Integer> {
}
