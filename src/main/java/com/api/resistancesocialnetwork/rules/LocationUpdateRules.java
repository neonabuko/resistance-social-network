package com.api.resistancesocialnetwork.rules;


import com.api.resistancesocialnetwork.model.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationUpdateRules {
    private final DataFormatRules dataFormatRules;

    public LocationUpdateRules(DataFormatRules dataFormatRules) {
        this.dataFormatRules = dataFormatRules;
    }

    public void formatLocationParams(Location location) {
        dataFormatRules.formatLocation(location);
    }
}
