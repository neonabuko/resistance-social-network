package com.api.resistancesocialnetwork.rules;


import com.api.resistancesocialnetwork.entity.Location;
import com.api.resistancesocialnetwork.request.facade.LocationUpdateFacade;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import com.api.resistancesocialnetwork.usecase.formatters.FormatEntities;
import org.springframework.stereotype.Component;

@Component
public class LocationUpdateRules {
    private final FormatEntities formatEntities;

    public LocationUpdateRules(FormatEntities formatEntities) {
        this.formatEntities = formatEntities;
    }

    public void handle(LocationUpdateFacade facade) throws ResistanceSocialNetworkException {
        assert_facade_provided(facade);
        assert_new_location_provided(facade.location());
        formatEntities.formatLocation(facade.location());
    }

    private void assert_facade_provided(LocationUpdateFacade facade) {
        if (facade == null) throw new ResistanceSocialNetworkException("must provide parameters for location update");
    }
    private void assert_new_location_provided(Location location) {
        if (location == null) throw new ResistanceSocialNetworkException("must provide new location");
    }
}
