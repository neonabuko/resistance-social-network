package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.LocationRepositoryInMemory;
import com.api.resistancesocialnetwork.request.DTO.LocationUpdateDTO;
import com.api.resistancesocialnetwork.rules.LocationUpdateRules;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationUpdateUseCaseTest {
    private final LocationRepositoryInMemory locationRepoInMem = new LocationRepositoryInMemory();

    @Test
    void should_update_location() {
        Location location = new Location(54.4, 12.2, "xereca");
        location.setId(1);
        locationRepoInMem.save(location);

        LocationUpdateDTO locationUpdateDTO = new LocationUpdateDTO(location.getId(), 3453.3, 22.2, "base");

        new LocationUpdateUseCase(locationRepoInMem, new LocationUpdateRules()).handle(locationUpdateDTO);
        
        Location newLocation = locationRepoInMem.findById(location.getId()).get();

        assertEquals(90, newLocation.getLatitude());
        assertEquals(22.2, newLocation.getLongitude());
        assertEquals("base", newLocation.getBase());
    }
}
