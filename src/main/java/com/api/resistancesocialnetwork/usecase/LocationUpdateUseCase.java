package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.LocationRepository;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.RebelRepository;
import com.api.resistancesocialnetwork.request.facade.LocationUpdateFacade;
import com.api.resistancesocialnetwork.rules.LocationUpdateRules;
import org.springframework.stereotype.Service;

@Service
public class LocationUpdateUseCase {
    private final RebelRepository rebelRepository;
    private final LocationRepository locationRepository;
    private final LocationUpdateRules locationUpdateRules;

    public LocationUpdateUseCase(RebelRepository rebelRepository,
                                 LocationRepository locationRepository,
                                 LocationUpdateRules locationUpdateRules) {
        this.rebelRepository = rebelRepository;
        this.locationRepository = locationRepository;
        this.locationUpdateRules = locationUpdateRules;
    }

    public void handle(LocationUpdateFacade locationUpdateFacade) {
        locationUpdateRules.handle(locationUpdateFacade);
        Integer rebelId = locationUpdateFacade.location().getId();

        Rebel rebel_in_repository = rebelRepository.findById(rebelId).orElseThrow(
                () -> new IllegalArgumentException("rebel not found")
        );

        Location location_in_repository = rebel_in_repository.getLocation();

        Location newLocation = locationUpdateFacade.location();

        location_in_repository.setLocation(
                newLocation.getLatitude(),
                newLocation.getLongitude(),
                newLocation.getBase()
        );

        locationRepository.save(location_in_repository);
    }
}