package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.usecase.formatters.FormatData;
import com.api.resistancesocialnetwork.usecase.formatters.FormatEntities;
import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.request.facade.SignupFacade;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SignupRulesTest {
    private final FormatEntities formatEntities = new FormatEntities(new FormatData());
    private final SignupRules signupRules = new SignupRules(formatEntities);
    private final Rebel rebel = new Rebel("dummy", 18, "male");
    private final Location location = new Location(0.0, 0.0, "base");
    private final Inventory inventory = new Inventory(Arrays.asList(new Item("food", 1)));

    @Test
    void should_throw_ResistanceSocialNetworkException_when_facade_null() {
        SignupFacade signupFacade = null;

        Exception e = assertThrows(ResistanceSocialNetworkException.class, () ->
                signupRules.handle(signupFacade)
        );

        assertTrue(e.getMessage().contains("must provide signup parameters"));
    }

    @Test
    void should_throw_ResistanceSocialNetworkException_when_rebel_null() {
        SignupFacade signupFacade = new SignupFacade(null, location, inventory);

        Exception e = assertThrows(ResistanceSocialNetworkException.class, () ->
                signupRules.handle(signupFacade)
        );

        assertTrue(e.getMessage().contains("must provide rebel parameters"));
    }

    @Test
    void should_throw_ResistanceSocialNetworkException_when_location_null() {
        SignupFacade signupFacade = new SignupFacade(rebel, null, inventory);

        Exception e = assertThrows(ResistanceSocialNetworkException.class, () ->
                signupRules.handle(signupFacade)
        );

        assertTrue(e.getMessage().contains("must provide location parameters"));
    }

    @Test
    void should_throw_ResistanceSocialNetworkException_when_inventory_null() {
        SignupFacade signupFacade = new SignupFacade(rebel, location, null);

        Exception e = assertThrows(ResistanceSocialNetworkException.class, () ->
                signupRules.handle(signupFacade)
        );

        assertTrue(e.getMessage().contains("must provide inventory parameters"));
    }

    @Test
    void should_throw_ResistanceSocialNetworkException_when_item_null() {
        Item nullItem = null;
        inventory.setItems(Arrays.asList(nullItem));

        SignupFacade signupFacade = new SignupFacade(rebel, location, inventory);

        Exception e = assertThrows(ResistanceSocialNetworkException.class, () ->
                signupRules.handle(signupFacade)
        );
        assertTrue(e.getMessage().contains("must provide item parameters"));
    }
}