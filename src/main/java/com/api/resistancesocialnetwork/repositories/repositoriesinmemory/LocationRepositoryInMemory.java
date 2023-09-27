package com.api.resistancesocialnetwork.repositories.repositoriesinmemory;

import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.LocationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocationRepositoryInMemory implements LocationRepository {
    private final List<Location> locationList;

    public LocationRepositoryInMemory() {
        locationList = new ArrayList<>();
    }

    @Override
    public void save(Location location) {
        locationList.add(location);
    }

    @Override
    public Optional<Location> findById(Integer id) {
        return locationList.stream().filter(location -> location.getId().equals(id)).findFirst();
    }

    @Override
    public boolean existsById(Integer id) {
        return locationList.stream().anyMatch(location -> location.getId().equals(id));
    }

    @Override
    public List<Location> findAll() {
        return locationList;
    }
}
