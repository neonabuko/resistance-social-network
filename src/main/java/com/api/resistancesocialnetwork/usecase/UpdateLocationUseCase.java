package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.entity.Location;
import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.facade.UpdateLocationFacade;
import com.api.resistancesocialnetwork.repository.repositoryinterfaces.LocationRepository;
import com.api.resistancesocialnetwork.repository.repositoryinterfaces.RebelRepository;
import com.api.resistancesocialnetwork.rules.UpdateLocationRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceException;
import org.springframework.stereotype.Service;

@Service
public class UpdateLocationUseCase {
    private final RebelRepository rebelRepository;
    private final LocationRepository locationRepository;
    private final UpdateLocationRules updateLocationRules;

    public UpdateLocationUseCase(RebelRepository rebelRepository,
                                 LocationRepository locationRepository,
                                 UpdateLocationRules updateLocationRules) {
        this.rebelRepository = rebelRepository;
        this.locationRepository = locationRepository;
        this.updateLocationRules = updateLocationRules;
    }

    public void handle(UpdateLocationFacade updateLocationFacade) throws ResistanceException {
        updateLocationRules.handle(updateLocationFacade);
        Integer rebelId = updateLocationFacade.location().getId();

        Rebel rebel_in_repository = rebelRepository.findById(rebelId).orElseThrow(
                () -> new ResistanceException("rebel not found")
        );

        Location location_in_repository = rebel_in_repository.getLocation();

        Location newLocation = updateLocationFacade.location();

        location_in_repository.setLocation(
                newLocation.getLatitude(),
                newLocation.getLongitude(),
                newLocation.getBase()
        );

        locationRepository.save(location_in_repository);
    }
}