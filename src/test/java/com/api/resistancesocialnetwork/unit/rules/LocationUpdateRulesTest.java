package com.api.resistancesocialnetwork.unit.rules;

import com.api.resistancesocialnetwork.entity.Location;
import com.api.resistancesocialnetwork.facade.LocationUpdateFacade;
import com.api.resistancesocialnetwork.rules.LocationUpdateRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import com.api.resistancesocialnetwork.usecase.formatters.FormatData;
import com.api.resistancesocialnetwork.usecase.formatters.FormatEntities;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LocationUpdateRulesTest {
    private final FormatEntities formatEntities = new FormatEntities(new FormatData());
    private final LocationUpdateRules locationUpdateRules = new LocationUpdateRules(formatEntities);

    @Test
    void should_throw_ResistanceSocialNetworkException_when_facade_null() {
        LocationUpdateFacade locationUpdateFacade = null;
        Exception e = assertThrows(ResistanceSocialNetworkException.class, () ->
                locationUpdateRules.handle(locationUpdateFacade)
        );
        assertTrue(e.getMessage().contains("must provide parameters for location update"));
    }
    @Test
    void should_throw_ResistanceSocialNetworkException_when_new_location_null() {
        Location newLocation = null;
        LocationUpdateFacade locationUpdateFacade = new LocationUpdateFacade(newLocation);
        
        Exception e = assertThrows(ResistanceSocialNetworkException.class, () ->
                locationUpdateRules.handle(locationUpdateFacade));
        assertTrue(e.getMessage().contains("must provide new location"));
    }
}