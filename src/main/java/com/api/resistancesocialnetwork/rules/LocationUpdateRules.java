package com.api.resistancesocialnetwork.rules;


import com.api.resistancesocialnetwork.model.Location;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LocationUpdateRules {
    private final FormatEntities formatEntities;

    public LocationUpdateRules(FormatEntities formatEntities) {
        this.formatEntities = formatEntities;
    }

    public void handle(Location referencedLocation) {
        assert_new_location_provided(referencedLocation);
        assert_new_location_id_provided(referencedLocation);
        formatEntities.formatLocation(referencedLocation);
    }

    private void assert_new_location_provided(Location location) {
        Optional.ofNullable(location).orElseThrow(
                () -> new IllegalArgumentException("must provide new location")
        );
    }

    private void assert_new_location_id_provided(Location location) {
        Optional.ofNullable(location.getId()).orElseThrow(
                () -> new IllegalArgumentException("must provide location id")
        );
    }
}
