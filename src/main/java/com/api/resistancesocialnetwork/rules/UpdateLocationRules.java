package com.api.resistancesocialnetwork.rules;


import com.api.resistancesocialnetwork.entity.Location;
import com.api.resistancesocialnetwork.facade.UpdateLocationFacade;
import com.api.resistancesocialnetwork.rules.commons.ResistanceException;
import com.api.resistancesocialnetwork.usecase.formatters.FormatEntities;
import org.springframework.stereotype.Component;

@Component
public class UpdateLocationRules {
    private final FormatEntities formatEntities;

    public UpdateLocationRules(FormatEntities formatEntities) {
        this.formatEntities = formatEntities;
    }

    public void handle(UpdateLocationFacade facade) throws ResistanceException {
        assert_facade_provided(facade);
        assert_new_location_provided(facade.location());
        formatEntities.formatLocation(facade.location());
    }

    private void assert_facade_provided(UpdateLocationFacade facade) {
        if (facade == null) throw new ResistanceException("must provide parameters for location update");
    }
    private void assert_new_location_provided(Location location) {
        if (location == null) throw new ResistanceException("must provide new location");
    }
}
