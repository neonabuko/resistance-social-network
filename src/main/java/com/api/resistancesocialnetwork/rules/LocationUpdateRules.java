package com.api.resistancesocialnetwork.rules;


import com.api.resistancesocialnetwork.model.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationUpdateRules {
    private final FormatEntities formatEntities;

    public LocationUpdateRules(FormatEntities formatEntities) {
        this.formatEntities = formatEntities;
    }

    public void formatLocationParams(Location location) {
        formatEntities.formatLocation(location);
    }
}
