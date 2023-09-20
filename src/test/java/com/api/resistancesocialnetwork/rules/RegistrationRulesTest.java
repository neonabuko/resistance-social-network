package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegistrationRulesTest {
    private final RegistrationRules registrationRules = new RegistrationRules();
    private final Rebel rebel = new Rebel("dummy", 18, "male");
    private final Location location = new Location(0.0, 0.0, "base");
    private final Inventory inventory = new Inventory(new ArrayList<>(Arrays.asList(
            new Item("food", 1),
            new Item("water", 2)
    )));

    @Test
    void should_return_name_as_undefined_when_not_provided() {
        rebel.setStats(null, 18, "male");
        Rebel formattedRebel = (Rebel) registrationRules.format(rebel, location, inventory).get(0);
        assertEquals("undefined", formattedRebel.getName());
    }

    @Test
    void should_return_30char_name_when_name_over_30_char() {
        rebel.setStats("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 18, "male");
        Rebel formattedRebel = (Rebel) registrationRules.format(rebel, location, inventory).get(0);
        assertEquals(30, formattedRebel.getName().length());
    }

    @Test
    void should_return_age_0_if_not_provided() {
        rebel.setStats("dummy", null, "base");
        Rebel formattedRebel = (Rebel) registrationRules.format(rebel, location, inventory).get(0);
        assertEquals(0, formattedRebel.getAge());
    }

    @Test
    void should_return_age_0_if_negative_provided() {
        rebel.setStats("dummy", -1, "base");
        Rebel formattedRebel = (Rebel) registrationRules.format(rebel, location, inventory).get(0);
        assertEquals(0, formattedRebel.getAge());
    }

    @Test
    void should_return_age_100_if_over_100_provided() {
        rebel.setStats("dummy", 12312, "base");
        Rebel formattedRebel = (Rebel) registrationRules.format(rebel, location, inventory).get(0);
        assertEquals(100, formattedRebel.getAge());
    }

    @Test
    void should_return_gender_undefined_if_not_provided() {
        rebel.setStats("dummy", 12, null);
        Rebel formattedRebel = (Rebel) registrationRules.format(rebel, location, inventory).get(0);
        assertEquals("undefined", formattedRebel.getGender());
    }

    @Test
    void should_return_gender_30char_when_over_30char_provided() {
        rebel.setStats("dummy", 12, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        Rebel formattedRebel = (Rebel) registrationRules.format(rebel, location, inventory).get(0);
        assertEquals(30, formattedRebel.getGender().length());
    }
}