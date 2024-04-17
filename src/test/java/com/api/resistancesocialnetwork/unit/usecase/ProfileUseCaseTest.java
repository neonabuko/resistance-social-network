package com.api.resistancesocialnetwork.unit.usecase;

import com.api.resistancesocialnetwork.entity.*;
import com.api.resistancesocialnetwork.enums.UserRole;
import com.api.resistancesocialnetwork.facade.ItemFacade;
import com.api.resistancesocialnetwork.facade.ProfileFacade;
import com.api.resistancesocialnetwork.repository.repositoriesinmemory.InventoryRepositoryInMemory;
import com.api.resistancesocialnetwork.repository.repositoriesinmemory.LocationRepositoryInMemory;
import com.api.resistancesocialnetwork.repository.repositoriesinmemory.RebelRepositoryInMemory;
import com.api.resistancesocialnetwork.repository.repositoriesinmemory.ResistanceUserRepositoryInMemory;
import com.api.resistancesocialnetwork.rules.ProfileRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceException;
import com.api.resistancesocialnetwork.usecase.ProfileUseCase;
import com.api.resistancesocialnetwork.usecase.formatters.FormatData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProfileUseCaseTest {
    private final RebelRepositoryInMemory rebelRepoInMem = new RebelRepositoryInMemory();
    private final InventoryRepositoryInMemory inventoryRepoInMem = new InventoryRepositoryInMemory();
    private final LocationRepositoryInMemory locationRepoInMem = new LocationRepositoryInMemory();
    private final FormatData formatData = new FormatData();
    private final ProfileRules signUpRules = new ProfileRules(formatData);
    private final ResistanceUserRepositoryInMemory userRepositoryInMem = new ResistanceUserRepositoryInMemory();
    private final ProfileUseCase profileUseCase = new ProfileUseCase(signUpRules, userRepositoryInMem);
    private Rebel luke = new Rebel("luke", 18, "male");
    private Location lukeLocation = new Location(0.2, 21.3, "base/galaxy");
    private Inventory lukeInv = new Inventory(Arrays.asList(new Item("doritos", 1)));
    private ProfileFacade profileFacade;
    private final String login = "jooj";

    @Test
    @DisplayName("should save rebel with location and inventory")
    void should_save_rebel_with_location_and_inventory() {
        var jooj = new ResistanceUser("jooj", "123", UserRole.USER);
        userRepositoryInMem.save(jooj);

        profileFacade = new ProfileFacade(
                "luke",
                18,
                "male",
                22.2,
                22.2,
                "base",
                List.of(new ItemFacade("food", 1))
        );
        profileUseCase.handle(profileFacade, login);

        assertNotEquals(null, jooj.getRebel());
        assertNotEquals(null, jooj.getLocation());
        assertNotEquals(null, jooj.getInventory());
    }

    @Test
    @DisplayName("should not save if rebel not provided")
    void should_not_save_anything_if_rebel_null() {
        luke = null;
        profileFacade = new ProfileFacade(
                "luke",
                18,
                "male",
                22.2,
                22.2,
                "base",
                List.of(new ItemFacade("food", 1))
        );
        try {
            profileUseCase.handle(profileFacade, login);
        } catch (Exception ignored) {}

        assertTrue(rebelRepoInMem.findAll().isEmpty());
        assertTrue(locationRepoInMem.findAll().isEmpty());
        assertTrue(inventoryRepoInMem.findAll().isEmpty());
    }

    @Test
    @DisplayName("should not save if location not provided")
    void should_not_save_anything_if_location_null() {
        lukeLocation = null;
        profileFacade = new ProfileFacade(
                "luke",
                18,
                "male",
                22.2,
                22.2,
                "base",
                List.of(new ItemFacade("food", 1))
        );
        try {
            profileUseCase.handle(profileFacade, login);
        } catch (Exception ignored) {}

        assertTrue(rebelRepoInMem.findAll().isEmpty());
        assertTrue(locationRepoInMem.findAll().isEmpty());
        assertTrue(inventoryRepoInMem.findAll().isEmpty());
    }

    @Test
    @DisplayName("should not save if inventory not provided")
    void should_not_save_anything_if_inventory_null() {
        lukeInv = null;
        profileFacade = new ProfileFacade(
                "luke",
                18,
                "male",
                22.2,
                22.2,
                "base",
                List.of(new ItemFacade("food", 1))
        );

        try {
            profileUseCase.handle(profileFacade, login);
        } catch (Exception ignored) {}

        assertTrue(rebelRepoInMem.findAll().isEmpty());
        assertTrue(locationRepoInMem.findAll().isEmpty());
        assertTrue(inventoryRepoInMem.findAll().isEmpty());
    }

    @Test
    @DisplayName("should not save if latitude not provided")
    void should_not_save_anything_if_latitude_null() {
        lukeLocation.setLocation(null, 23.2, "base");
        profileFacade = new ProfileFacade(
                "luke",
                18,
                "male",
                22.2,
                22.2,
                "base",
                List.of(new ItemFacade("food", 1))
        );

        try {
            profileUseCase.handle(profileFacade, login);
        } catch (Exception ignored) {}

        assertTrue(rebelRepoInMem.findAll().isEmpty());
        assertTrue(locationRepoInMem.findAll().isEmpty());
        assertTrue(inventoryRepoInMem.findAll().isEmpty());
    }

    @Test
    @DisplayName("should not save if longitude not provided")
    void should_not_save_anything_if_longitude_null() {
        lukeLocation.setLocation(23.2, null, "base");
        profileFacade = new ProfileFacade(
                "luke",
                18,
                "male",
                22.2,
                22.2,
                "base",
                List.of(new ItemFacade("food", 1))
        );

        try {
            profileUseCase.handle(profileFacade, login);
        } catch (Exception ignored) {}

        assertTrue(rebelRepoInMem.findAll().isEmpty());
        assertTrue(locationRepoInMem.findAll().isEmpty());
        assertTrue(inventoryRepoInMem.findAll().isEmpty());
    }

    @Test
    @DisplayName("should not save if no items provided")
    void should_not_save_anything_if_item_null() {
        var jooj = new ResistanceUser("jooj", "123", UserRole.USER);
        userRepositoryInMem.saveInMem(jooj);
        jooj.setId(1);

        Item nullItem = null;
        lukeInv.setItems(Arrays.asList(nullItem));
        profileFacade = new ProfileFacade(
                "luke",
                18,
                "male",
                22.2,
                22.2,
                "base",
                List.of(new ItemFacade("food", 1))
        );

        try {
            profileUseCase.handle(profileFacade, login);
        } catch (ResistanceException ignored) {
        }

        assertTrue(rebelRepoInMem.findAll().isEmpty());
        assertTrue(locationRepoInMem.findAll().isEmpty());
        assertTrue(inventoryRepoInMem.findAll().isEmpty());
    }

    @Test
    @DisplayName("should not save profile twice")
    void should_not_be_able_to_save_profile_twice() {
        var jooj = new ResistanceUser("jooj", "123", UserRole.USER);
        userRepositoryInMem.saveInMem(jooj);
        jooj.setId(1);
        profileFacade = new ProfileFacade(
                "luke",
                18,
                "male",
                22.2,
                22.2,
                "base",
                List.of(new ItemFacade("food", 1))
        );
        profileUseCase.handle(profileFacade, login);
        Exception e = assertThrows(ResistanceException.class, () ->
                profileUseCase.handle(profileFacade, login)
        );

        assertTrue(e.getMessage().contains("profile already set"));
    }
}