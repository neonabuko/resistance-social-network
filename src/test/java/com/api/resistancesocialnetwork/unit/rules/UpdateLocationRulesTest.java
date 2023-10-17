package com.api.resistancesocialnetwork.unit.rules;

import com.api.resistancesocialnetwork.entity.Location;
import com.api.resistancesocialnetwork.facade.UpdateLocationFacade;
import com.api.resistancesocialnetwork.rules.UpdateLocationRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceException;
import com.api.resistancesocialnetwork.usecase.formatters.FormatData;
import com.api.resistancesocialnetwork.usecase.formatters.FormatEntities;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UpdateLocationRulesTest {
    private final FormatEntities formatEntities = new FormatEntities(new FormatData());
    private final UpdateLocationRules updateLocationRules = new UpdateLocationRules(formatEntities);

    @Test
    void should_throw_ResistanceSocialNetworkException_when_facade_null() {
        UpdateLocationFacade updateLocationFacade = null;
        Exception e = assertThrows(ResistanceException.class, () ->
                updateLocationRules.handle(updateLocationFacade)
        );
        assertTrue(e.getMessage().contains("must provide parameters for location update"));
    }
    @Test
    void should_throw_ResistanceSocialNetworkException_when_new_location_null() {
        Location newLocation = null;
        UpdateLocationFacade updateLocationFacade = new UpdateLocationFacade(newLocation);
        
        Exception e = assertThrows(ResistanceException.class, () ->
                updateLocationRules.handle(updateLocationFacade));
        assertTrue(e.getMessage().contains("must provide new location"));
    }
}