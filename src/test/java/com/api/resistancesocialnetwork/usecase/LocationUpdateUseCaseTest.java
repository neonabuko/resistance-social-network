package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.LocationRepositoryInMemory;
import com.api.resistancesocialnetwork.rules.LocationUpdateRules;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationUpdateUseCaseTest {
    private final LocationRepositoryInMemory locationRepository = new LocationRepositoryInMemory();
    private final LocationUpdateRules locationUpdateRules = new LocationUpdateRules(locationRepository);
    private final LocationUpdateUseCase locationUpdateUseCase = new LocationUpdateUseCase(locationRepository, locationUpdateRules);

    @Test
    void should_save_new_location(){
        Location oldLocation = new Location(150.1,12.2,"xereca");
        locationRepository.save(oldLocation);

        Double newLatitude = 38.1;
        Double newLongitude = 64.7;
        String newBase = "jumanji";

        locationUpdateUseCase.handle(oldLocation.getId(), newLatitude, newLongitude, newBase);
        Location newLocation = locationRepository.findById(oldLocation.getId()).get();

        assertEquals(newLatitude, newLocation.getLatitude());
        assertEquals(newLongitude, newLocation.getLongitude());
        assertEquals(newBase, newLocation.getBase());
    }
}
