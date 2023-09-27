package com.api.resistancesocialnetwork.rules;


import com.api.resistancesocialnetwork.formatters.FormatEntities;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.request.facade.LocationUpdateFacade;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LocationUpdateRules {
    private final FormatEntities formatEntities;

    public LocationUpdateRules(FormatEntities formatEntities) {
        this.formatEntities = formatEntities;
    }

    public void handle(LocationUpdateFacade locationUpdateFacade) {
        formatEntities.formatLocation(locationUpdateFacade.location());
        assert_rebelId_provided_in_DTO(locationUpdateFacade.location());
    }

    private void assert_rebelId_provided_in_DTO(Location location) {
        Optional.ofNullable(location.getId()).orElseThrow(
                () -> new IllegalArgumentException("must provide rebel id")
        );
    }
}
