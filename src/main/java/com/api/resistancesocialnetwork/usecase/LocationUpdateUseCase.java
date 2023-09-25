package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.LocationRepository;
import com.api.resistancesocialnetwork.request.DTO.LocationUpdateDTO;
import com.api.resistancesocialnetwork.rules.LocationUpdateRules;
import org.springframework.stereotype.Service;

@Service
public class LocationUpdateUseCase {
    private final LocationRepository locationRepository;
    private final LocationUpdateRules locationUpdateRules;

    public LocationUpdateUseCase(LocationRepository locationRepository, LocationUpdateRules locationUpdateRules) {
        this.locationRepository = locationRepository;
        this.locationUpdateRules = locationUpdateRules;
    }

    public void handle(LocationUpdateDTO locationUpdateDTO) {
        locationUpdateRules.handle(locationUpdateDTO.location());

        Location referencedLocation = locationRepository.findById(locationUpdateDTO.location().getId()).orElseThrow(
                () -> new IllegalArgumentException("location not found")
        );

        Location newLocation = locationUpdateDTO.location();

        referencedLocation.setLocation(
                newLocation.getLatitude(),
                newLocation.getLongitude(),
                newLocation.getBase()
        );
    }
}