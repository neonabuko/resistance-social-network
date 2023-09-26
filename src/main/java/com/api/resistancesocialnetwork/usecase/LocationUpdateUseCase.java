package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.LocationRepository;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.RebelRepository;
import com.api.resistancesocialnetwork.request.DTO.LocationUpdateDTO;
import com.api.resistancesocialnetwork.rules.LocationUpdateRules;
import org.springframework.stereotype.Service;

@Service
public class LocationUpdateUseCase {
    private final RebelRepository rebelRepository;
    private final LocationRepository locationRepository;
    private final LocationUpdateRules locationUpdateRules;

    public LocationUpdateUseCase(RebelRepository rebelRepository, LocationRepository locationRepository, LocationUpdateRules locationUpdateRules) {
        this.rebelRepository = rebelRepository;
        this.locationRepository = locationRepository;
        this.locationUpdateRules = locationUpdateRules;
    }

    public void handle(LocationUpdateDTO locationUpdateDTO) {
        locationUpdateRules.handle(locationUpdateDTO);
        Integer rebelId = locationUpdateDTO.location().getId();

        Rebel rebel_in_repository = rebelRepository.findById(rebelId).orElseThrow(
                () -> new IllegalArgumentException("rebel not found")
        );

        Location location_in_repository = rebel_in_repository.getLocation();

        Location newLocation = locationUpdateDTO.location();

        location_in_repository.setLocation(
                newLocation.getLatitude(),
                newLocation.getLongitude(),
                newLocation.getBase()
        );

        locationRepository.save(location_in_repository);
    }
}