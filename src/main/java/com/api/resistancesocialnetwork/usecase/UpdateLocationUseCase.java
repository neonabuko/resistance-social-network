package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.entity.Location;
import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.entity.User;
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

    public void handle(UpdateLocationFacade updateLocationFacade, User user) throws ResistanceException {
        Rebel rebel = user.getRebel().orElseThrow(
                () -> new ResistanceException("rebel not registered")
        );

        Location location = user.getLocation().get();

        Location newLocation = updateLocationRules.handle(updateLocationFacade);

        location.setLocation(
                newLocation.getLatitude(),
                newLocation.getLongitude(),
                newLocation.getBase()
        );

        locationRepository.save(location);
    }
}