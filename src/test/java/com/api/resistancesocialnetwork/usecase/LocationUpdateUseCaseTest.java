package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.formatters.FormatData;
import com.api.resistancesocialnetwork.formatters.FormatEntities;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.LocationRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.RebelRepositoryInMemory;
import com.api.resistancesocialnetwork.request.DTO.LocationUpdateDTO;
import com.api.resistancesocialnetwork.rules.LocationUpdateRules;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationUpdateUseCaseTest {
    private final LocationRepositoryInMemory locationRepoInMem = new LocationRepositoryInMemory();
    private final FormatEntities formatEntities = new FormatEntities(new FormatData());
    private final RebelRepositoryInMemory rebelRepositoryInMem = new RebelRepositoryInMemory();
    Rebel rebel = new Rebel("name", 18, "male");
    Location location_in_repo = new Location(54.4, 12.2, "xereca");

    @Test
    void should_update_location() {
        rebel.setId(1);

        location_in_repo.setId(1);
        locationRepoInMem.save(location_in_repo);

        rebel.setLocation(location_in_repo);
        rebelRepositoryInMem.save(rebel);

        Location toUpdateLocation = new Location(345345423525.23452345, -1283476.34323, "base");
        toUpdateLocation.setId(1);

        LocationUpdateDTO locationUpdateDTO = new LocationUpdateDTO(toUpdateLocation);

        new LocationUpdateUseCase(rebelRepositoryInMem, locationRepoInMem, new LocationUpdateRules(formatEntities)).handle(locationUpdateDTO);

        assertEquals(90, location_in_repo.getLatitude());
        assertEquals(-180, location_in_repo.getLongitude());
        assertEquals("base", location_in_repo.getBase());
    }
}
