package com.api.resistancesocialnetwork.unit.usecase;

import com.api.resistancesocialnetwork.entity.*;
import com.api.resistancesocialnetwork.enums.UserRole;
import com.api.resistancesocialnetwork.facade.ProfileFacade;
import com.api.resistancesocialnetwork.repository.repositoriesinmemory.InventoryRepositoryInMemory;
import com.api.resistancesocialnetwork.repository.repositoriesinmemory.LocationRepositoryInMemory;
import com.api.resistancesocialnetwork.repository.repositoriesinmemory.RebelRepositoryInMemory;
import com.api.resistancesocialnetwork.repository.repositoriesinmemory.UserRepositoryInMemory;
import com.api.resistancesocialnetwork.rules.ProfileRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import com.api.resistancesocialnetwork.usecase.ProfileUseCase;
import com.api.resistancesocialnetwork.usecase.formatters.FormatData;
import com.api.resistancesocialnetwork.usecase.formatters.FormatEntities;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProfileUseCaseTest {
    private final RebelRepositoryInMemory rebelRepoInMem = new RebelRepositoryInMemory();
    private final InventoryRepositoryInMemory inventoryRepoInMem = new InventoryRepositoryInMemory();
    private final LocationRepositoryInMemory locationRepoInMem = new LocationRepositoryInMemory();
    private final FormatEntities formatEntities = new FormatEntities(new FormatData());
    private final ProfileRules signUpRules = new ProfileRules(formatEntities);
    private final UserRepositoryInMemory userRepositoryInMem = new UserRepositoryInMemory();
    private final ProfileUseCase profileUseCase = new ProfileUseCase(signUpRules, userRepositoryInMem);
    private Rebel luke = new Rebel("luke", 18, "male");
    private Location lukeLocation = new Location(0.2, 21.3, "base/galaxy");
    private Inventory lukeInv = new Inventory(Arrays.asList(new Item("doritos", 1)));
    private ProfileFacade profileFacade;
    private final String login = "jooj";

    @Test
    @DisplayName("should always save rebel along with his location and inventory")
    void should_save_rebel_with_location_and_inventory() {
        User jooj = new User("jooj", "123", UserRole.USER);
        userRepositoryInMem.saveInMem(jooj);
        jooj.setId(1);

        luke.setId(1);
        lukeLocation.setId(1);
        lukeInv.setId(1);

        rebelRepoInMem.saveInMem(luke);
        profileFacade = new ProfileFacade(luke, lukeLocation, lukeInv);
        profileUseCase.handle(profileFacade, login);

        assertNotEquals(Optional.empty(), userRepositoryInMem.findUserBy(login));
        assertNotEquals(Optional.empty(), rebelRepoInMem.findById(luke.getId()));
        assertNotEquals(null, luke.getLocation());
        assertNotEquals(null, luke.getInventory());
    }

    @Test
    @DisplayName("should not save anything if rebel not provided")
    void should_not_save_anything_if_rebel_null() {
        luke = null;
        profileFacade = new ProfileFacade(luke, lukeLocation, lukeInv);
        try {
            profileUseCase.handle(profileFacade, login);
        } catch (Exception ignored) {}

        assertTrue(rebelRepoInMem.findAll().isEmpty());
        assertTrue(locationRepoInMem.findAll().isEmpty());
        assertTrue(inventoryRepoInMem.findAll().isEmpty());
    }

    @Test
    @DisplayName("should not save anything if location not provided")
    void should_not_save_anything_if_location_null() {
        lukeLocation = null;
        profileFacade = new ProfileFacade(luke, lukeLocation, lukeInv);
        try {
            profileUseCase.handle(profileFacade, login);
        } catch (Exception ignored) {}

        assertTrue(rebelRepoInMem.findAll().isEmpty());
        assertTrue(locationRepoInMem.findAll().isEmpty());
        assertTrue(inventoryRepoInMem.findAll().isEmpty());
    }

    @Test
    @DisplayName("should not save anything if inventory not provided")
    void should_not_save_anything_if_inventory_null() {
        lukeInv = null;
        profileFacade = new ProfileFacade(luke, lukeLocation, lukeInv);

        try {
            profileUseCase.handle(profileFacade, login);
        } catch (Exception ignored) {}

        assertTrue(rebelRepoInMem.findAll().isEmpty());
        assertTrue(locationRepoInMem.findAll().isEmpty());
        assertTrue(inventoryRepoInMem.findAll().isEmpty());
    }

    @Test
    @DisplayName("should not save anything if latitude not provided")
    void should_not_save_anything_if_latitude_null() {
        lukeLocation.setLocation(null, 23.2, "base");
        profileFacade = new ProfileFacade(luke, lukeLocation, lukeInv);

        try {
            profileUseCase.handle(profileFacade, login);
        } catch (Exception ignored) {}

        assertTrue(rebelRepoInMem.findAll().isEmpty());
        assertTrue(locationRepoInMem.findAll().isEmpty());
        assertTrue(inventoryRepoInMem.findAll().isEmpty());
    }

    @Test
    @DisplayName("should not save anything if longitude not provided")
    void should_not_save_anything_if_longitude_null() {
        lukeLocation.setLocation(23.2, null, "base");
        profileFacade = new ProfileFacade(luke, lukeLocation, lukeInv);

        try {
            profileUseCase.handle(profileFacade, login);
        } catch (Exception ignored) {}

        assertTrue(rebelRepoInMem.findAll().isEmpty());
        assertTrue(locationRepoInMem.findAll().isEmpty());
        assertTrue(inventoryRepoInMem.findAll().isEmpty());
    }

    @Test
    @DisplayName("should not save anything if no items provided")
    void should_not_save_anything_if_item_null() {
        Item nullItem = null;
        lukeInv.setItems(Arrays.asList(nullItem));
        profileFacade = new ProfileFacade(luke, lukeLocation, lukeInv);

        Exception e = assertThrows(ResistanceSocialNetworkException.class, () ->
                profileUseCase.handle(profileFacade, login)
        );

        assertTrue(rebelRepoInMem.findAll().isEmpty());
        assertTrue(locationRepoInMem.findAll().isEmpty());
        assertTrue(inventoryRepoInMem.findAll().isEmpty());
        assertTrue(e.getMessage().contains("must provide item parameters"));
    }

    @Test
    @DisplayName("should not be able to save profile twice")
    void should_not_be_able_to_save_profile_twice() {
        User jooj = new User("jooj", "123", UserRole.USER);
        userRepositoryInMem.saveInMem(jooj);
        jooj.setId(1);
        profileFacade = new ProfileFacade(this.luke, lukeLocation, lukeInv);
        profileUseCase.handle(profileFacade, login);
        Exception e = assertThrows(ResistanceSocialNetworkException.class, () ->
                profileUseCase.handle(profileFacade, login)
        );

        assertTrue(e.getMessage().contains("profile already set"));
    }
}