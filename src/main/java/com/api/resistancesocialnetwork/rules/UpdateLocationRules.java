package com.api.resistancesocialnetwork.rules;


import com.api.resistancesocialnetwork.entity.Location;
import com.api.resistancesocialnetwork.facade.UpdateLocationFacade;
import com.api.resistancesocialnetwork.rules.commons.ResistanceException;
import com.api.resistancesocialnetwork.usecase.formatters.FormatData;
import org.springframework.stereotype.Component;

@Component
public class UpdateLocationRules {
    private final FormatData formatData;

    public UpdateLocationRules(FormatData formatData) {
        this.formatData = formatData;
    }

    public Location handle(UpdateLocationFacade facade) throws ResistanceException {
        return formatLocation(facade.latitude(), facade.longitude(), facade.base());
    }

    private Location formatLocation(Double latitude, Double longitude, String base) {
        return new Location(
                formatData.formatCoordinate(latitude, 180),
                formatData.formatCoordinate(longitude, 90),
                formatData.formatString(base, 20, "base")
        );
    }
}
