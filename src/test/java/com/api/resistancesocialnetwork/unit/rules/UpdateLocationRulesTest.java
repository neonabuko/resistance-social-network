package com.api.resistancesocialnetwork.unit.rules;

import com.api.resistancesocialnetwork.facade.UpdateLocationFacade;
import com.api.resistancesocialnetwork.rules.UpdateLocationRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceException;
import com.api.resistancesocialnetwork.usecase.formatters.FormatData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UpdateLocationRulesTest {
    private final FormatData formatData = new FormatData();
    private final UpdateLocationRules updateLocationRules = new UpdateLocationRules(formatData);

    @Test
    void should_throw_ResistanceSocialNetworkException_when_new_latitude_null() {
        UpdateLocationFacade updateLocationFacade = new UpdateLocationFacade(null, 0.0, "base");
        
        Exception e = assertThrows(ResistanceException.class, () ->
                updateLocationRules.handle(updateLocationFacade));
        assertTrue(e.getMessage().contains("must provide coordinates"));
    }

    @Test
    void should_throw_ResistanceSocialNetworkException_when_new_longitude_null() {
        UpdateLocationFacade updateLocationFacade = new UpdateLocationFacade(0.0, null, "base");

        Exception e = assertThrows(ResistanceException.class, () ->
                updateLocationRules.handle(updateLocationFacade));
        assertTrue(e.getMessage().contains("must provide coordinates"));
    }

    @Test
    void should_throw_ResistanceSocialNetworkException_when_new_base_null() {
        UpdateLocationFacade updateLocationFacade = new UpdateLocationFacade(0.0, 0.0, null);

        Exception e = assertThrows(ResistanceException.class, () ->
                updateLocationRules.handle(updateLocationFacade));
        assertTrue(e.getMessage().contains("must provide base"));
    }
}