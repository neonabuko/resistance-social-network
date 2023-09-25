package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.LocationRepositoryInMemory;
import com.api.resistancesocialnetwork.request.DTO.LocationUpdateDTO;
import com.api.resistancesocialnetwork.rules.DataFormatRules;
import com.api.resistancesocialnetwork.rules.LocationUpdateRules;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationUpdateUseCaseTest {
    private final LocationRepositoryInMemory locationRepoInMem = new LocationRepositoryInMemory();
    private final DataFormatRules dataFormatRules = new DataFormatRules();

    @Test
    void should_update_location() {
        Location savedLocation = new Location(54.4, 12.2, "xereca");
        savedLocation.setId(1);
        locationRepoInMem.save(savedLocation);

        Location toUpdateLocation = new Location(345345423525.23452345, -1283476.34323, "base");
        toUpdateLocation.setId(1);

        LocationUpdateDTO locationUpdateDTO = new LocationUpdateDTO(toUpdateLocation);

        new LocationUpdateUseCase(locationRepoInMem, new LocationUpdateRules(dataFormatRules)).handle(locationUpdateDTO);

        assertEquals(90, savedLocation.getLatitude());
        assertEquals(-180, savedLocation.getLongitude());
        assertEquals("base", savedLocation.getBase());
    }
}
