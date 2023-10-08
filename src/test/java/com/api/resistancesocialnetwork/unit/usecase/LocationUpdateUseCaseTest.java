package com.api.resistancesocialnetwork.unit.usecase;

import com.api.resistancesocialnetwork.entity.Location;
import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.LocationRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.RebelRepositoryInMemory;
import com.api.resistancesocialnetwork.request.facade.LocationUpdateFacade;
import com.api.resistancesocialnetwork.rules.LocationUpdateRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import com.api.resistancesocialnetwork.usecase.LocationUpdateUseCase;
import com.api.resistancesocialnetwork.usecase.formatters.FormatData;
import com.api.resistancesocialnetwork.usecase.formatters.FormatEntities;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LocationUpdateUseCaseTest {
    private final LocationRepositoryInMemory locationRepoInMem = new LocationRepositoryInMemory();
    private final FormatEntities formatEntities = new FormatEntities(new FormatData());
    private final RebelRepositoryInMemory rebelRepositoryInMem = new RebelRepositoryInMemory();
    private final Rebel rebel = new Rebel("name", 18, "male");
    private final Location location_in_repo = new Location(54.4, 12.2, "xereca");

    @Test
    void should_update_location() {
        locationRepoInMem.save(location_in_repo);

        rebel.setLocation(location_in_repo);
        rebel.setId(1);
        rebelRepositoryInMem.save(rebel);

        Location toUpdateLocation = new Location(345345425.23452345, -1283476.34323, "base");
        toUpdateLocation.setId(1);

        LocationUpdateFacade locationUpdateFacade = new LocationUpdateFacade(toUpdateLocation);

        new LocationUpdateUseCase(rebelRepositoryInMem, locationRepoInMem, new LocationUpdateRules(formatEntities)).handle(locationUpdateFacade);

        assertEquals(90, location_in_repo.getLatitude());
        assertEquals(-180, location_in_repo.getLongitude());
        assertEquals("base", location_in_repo.getBase());
    }

    @Test
    void should_not_update_location_when_rebel_not_found() {
        Location toUpdateLocation = new Location(345345425.23452345, -1283476.34323, "base");

        LocationUpdateFacade locationUpdateFacade = new LocationUpdateFacade(toUpdateLocation);
        Exception e = assertThrows(ResistanceSocialNetworkException.class, () ->
                new LocationUpdateUseCase(
                        rebelRepositoryInMem,
                        locationRepoInMem,
                        new LocationUpdateRules(formatEntities)).handle(locationUpdateFacade)
        );

        assertTrue(e.getMessage().contains("rebel not found"));
    }
}
