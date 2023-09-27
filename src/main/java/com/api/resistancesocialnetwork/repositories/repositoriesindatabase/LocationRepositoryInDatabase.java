package com.api.resistancesocialnetwork.repositories.repositoriesindatabase;

import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.LocationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

interface LocationRepositoryJpa extends JpaRepository<Location, Integer> {
}

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
    public Optional<Location> findById(Integer id) {
        return adapter.findById(id);
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
