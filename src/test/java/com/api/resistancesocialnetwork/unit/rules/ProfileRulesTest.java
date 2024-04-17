package com.api.resistancesocialnetwork.unit.rules;

import com.api.resistancesocialnetwork.entity.Inventory;
import com.api.resistancesocialnetwork.entity.Item;
import com.api.resistancesocialnetwork.entity.Location;
import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.facade.ItemFacade;
import com.api.resistancesocialnetwork.facade.ProfileFacade;
import com.api.resistancesocialnetwork.rules.ProfileRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceException;
import com.api.resistancesocialnetwork.usecase.formatters.FormatData;
import com.api.resistancesocialnetwork.usecase.formatters.FormatEntities;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProfileRulesTest {
    private final FormatEntities formatEntities = new FormatEntities(new FormatData());
    private final FormatData formatData = new FormatData();
    private final ProfileRules profileRules = new ProfileRules(formatData);
    private final Rebel rebel = new Rebel("luke", 18, "male");
    private final Location location = new Location(0.0, 0.0, "base");
    private final Inventory inventory = new Inventory(new ArrayList<>(Arrays.asList(new Item("food", 1))));

    @Test
    void should_throw_ResistanceSocialNetworkException_when_no_item_provided() {
        inventory.getItems().clear();
        ProfileFacade profileFacade = new ProfileFacade(
                "luke",
                18,
                "male",
                22.2,
                22.2,
                "base",
                List.of(new ItemFacade("food", 1))
        );
        Exception e = assertThrows(ResistanceException.class, () ->
                profileRules.handle(rebel, location, inventory)
        );
        assertTrue(e.getMessage().contains("must provide at least one item"));
    }

    @Test
    void should_throw_ResistanceSocialNetworkException_when_item_null() {
        Item nullItem = null;
        inventory.setItems(Arrays.asList(nullItem));

        ProfileFacade profileFacade = new ProfileFacade(
                "luke",
                18,
                "male",
                22.2,
                22.2,
                "base",
                List.of(new ItemFacade("food", 1))
        );

        Exception e = assertThrows(ResistanceException.class, () ->
                profileRules.handle(rebel, location, inventory)
        );
        assertTrue(e.getMessage().contains("must provide item parameters"));
    }
}