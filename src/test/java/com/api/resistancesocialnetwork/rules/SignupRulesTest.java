package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.RebelRepositoryInMemory;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SignupRulesTest {
    private final RebelRepositoryInMemory rebelRepositoryInMemory = new RebelRepositoryInMemory();
    private final SignupRules signupRules = new SignupRules();
    private final Rebel rebel = new Rebel("dummy", 18, "male");
    private final Location location = new Location(0.0, 0.0, "base");
    private final Inventory inventory = new Inventory(Arrays.asList(new Item("food", 1), new Item("water", 2)));

    @Test
    void should_return_name_as_undefined_when_not_provided() {
        rebel.setStats(null, 18, "male");
        signupRules.format(rebel, location, inventory);
        rebelRepositoryInMemory.saveInMem(rebel);
        assertEquals("undefined", rebel.getName());
    }

    @Test
    void should_return_30char_name_when_name_over_30_char() {
        rebel.setStats("a".repeat(50), 18, "male");
        signupRules.format(rebel, location, inventory);
        rebelRepositoryInMemory.saveInMem(rebel);
        assertEquals(30, rebel.getName().length());
    }

    @Test
    void should_return_age_0_if_not_provided() {
        rebel.setStats("dummy", null, "base");
        signupRules.format(rebel, location, inventory);
        rebelRepositoryInMemory.saveInMem(rebel);
        assertEquals(0, rebel.getAge());
    }

    @Test
    void should_return_age_0_if_negative_provided() {
        rebel.setStats("dummy", -1, "base");
        signupRules.format(rebel, location, inventory);
        rebelRepositoryInMemory.saveInMem(rebel);
        assertEquals(0, rebel.getAge());
    }

    @Test
    void should_return_age_100_if_over_100_provided() {
        rebel.setStats("dummy", 12312, "base");
        signupRules.format(rebel, location, inventory);
        rebelRepositoryInMemory.saveInMem(rebel);
        assertEquals(100, rebel.getAge());
    }

    @Test
    void should_return_gender_undefined_if_not_provided() {
        rebel.setStats("dummy", 12, null);
        signupRules.format(rebel, location, inventory);
        rebelRepositoryInMemory.saveInMem(rebel);
        assertEquals("undefined", rebel.getGender());
    }

    @Test
    void should_return_gender_30char_when_over_30char_provided() {
        rebel.setStats("dummy", 12, "a".repeat(31));
        signupRules.format(rebel, location, inventory);
        rebelRepositoryInMemory.saveInMem(rebel);
        assertEquals(30, rebel.getGender().length());
    }
}