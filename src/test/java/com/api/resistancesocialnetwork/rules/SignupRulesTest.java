package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.formatters.FormatData;
import com.api.resistancesocialnetwork.formatters.FormatEntities;
import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.RebelRepositoryInMemory;
import com.api.resistancesocialnetwork.request.facade.SignupFacade;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SignupRulesTest {
    private final RebelRepositoryInMemory rebelRepositoryInMem = new RebelRepositoryInMemory();
    private final FormatEntities formatEntities = new FormatEntities(new FormatData());
    private final SignupRules signupRules = new SignupRules(formatEntities);
    private final Rebel rebel = new Rebel("dummy", 18, "male");
    private final Location location = new Location(0.0, 0.0, "base");
    private final Inventory inventory = new Inventory(Arrays.asList(new Item("food", 1), new Item("water", 2)));

    @Test
    void should_throw_IllegalStateException_when_name_null() {
        rebel.setStats(null, 18, "male");
        SignupFacade signup = new SignupFacade(rebel, location, inventory);
        Exception e = assertThrows(ResistanceSocialNetworkException.class, () ->
                signupRules.handle(signup)
        );
        rebelRepositoryInMem.save(rebel);
        assertTrue(e.getMessage().contains("must provide rebel name"));
    }

    @Test
    void should_return_30_character_name_when_name_over_30_character_provided() {
        rebel.setStats("a".repeat(50), 18, "male");
        SignupFacade signup = new SignupFacade(rebel, location, inventory);
        signupRules.handle(signup);
        rebelRepositoryInMem.save(rebel);
        assertEquals(30, rebel.getName().length());
    }

    @Test
    void should_throw_IllegalStateException_if_age_not_provided() {
        rebel.setStats("dummy", null, "base");
        SignupFacade signup = new SignupFacade(rebel, location, inventory);
        Exception e = assertThrows(ResistanceSocialNetworkException.class, () ->
                signupRules.handle(signup)
        );
        rebelRepositoryInMem.save(rebel);
        assertTrue(e.getMessage().contains("must provide rebel age"));
    }

    @Test
    void should_return_age_0_if_negative_provided() {
        rebel.setStats("dummy", -1, "base");
        SignupFacade signup = new SignupFacade(rebel, location, inventory);
        signupRules.handle(signup);
        rebelRepositoryInMem.save(rebel);
        assertEquals(0, rebel.getAge());
    }

    @Test
    void should_return_age_100_if_over_100_provided() {
        rebel.setStats("dummy", 12312, "base");
        SignupFacade signup = new SignupFacade(rebel, location, inventory);
        signupRules.handle(signup);
        rebelRepositoryInMem.save(rebel);
        assertEquals(100, rebel.getAge());
    }

    @Test
    void should_throw_IllegalStateException_when_gender_null() {
        rebel.setStats("dummy", 12, null);
        SignupFacade signup = new SignupFacade(rebel, location, inventory);
        Exception e = assertThrows(ResistanceSocialNetworkException.class, () ->
                signupRules.handle(signup)
        );
        rebelRepositoryInMem.save(rebel);
        assertTrue(e.getMessage().contains("must provide rebel gender"));
    }

    @Test
    void should_return_30_characters_gender_when_over_30_characters_provided() {
        rebel.setStats("dummy", 12, "a".repeat(31));
        SignupFacade signup = new SignupFacade(rebel, location, inventory);
        signupRules.handle(signup);
        rebelRepositoryInMem.save(rebel);
        assertEquals(30, rebel.getGender().length());
    }

    @Test
    void should_throw_IllegalStateException_when_null_item_provided() {
        Item nullItem = null;
        inventory.setItems(Arrays.asList(nullItem));
        SignupFacade signup = new SignupFacade(rebel, location, inventory);
        Exception e = assertThrows(ResistanceSocialNetworkException.class, () ->
                signupRules.handle(signup)
        );

        assertTrue(e.getMessage().contains("must provide item parameters"));
    }
}