package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.entity.Inventory;
import com.api.resistancesocialnetwork.entity.Item;
import com.api.resistancesocialnetwork.entity.Location;
import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.InventoryRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.LocationRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.RebelRepositoryInMemory;
import com.api.resistancesocialnetwork.request.facade.SignupFacade;
import com.api.resistancesocialnetwork.rules.SignupRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import com.api.resistancesocialnetwork.usecase.formatters.FormatData;
import com.api.resistancesocialnetwork.usecase.formatters.FormatEntities;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SignupUseCaseTest {
    private final RebelRepositoryInMemory rebelRepoInMem = new RebelRepositoryInMemory();
    private final InventoryRepositoryInMemory inventoryRepoInMem = new InventoryRepositoryInMemory();
    private final LocationRepositoryInMemory locationRepoInMem = new LocationRepositoryInMemory();
    private final FormatEntities formatEntities = new FormatEntities(new FormatData());
    private final SignupRules signUpRules = new SignupRules(formatEntities);
    private final SignupUseCase signupUseCase =
            new SignupUseCase(rebelRepoInMem, signUpRules);
    private Rebel luke = new Rebel("luke", 18, "male");
    private Location lukeLocation = new Location(0.2, 21.3, "base/galaxy");
    private Inventory lukeInv = new Inventory(Arrays.asList(new Item("doritos", 1)));
    private SignupFacade signupFacade;

    @Test
    @DisplayName("should always save rebel along with his location and inventory")
    void should_save_rebel_with_location_and_inventory() {
        luke.setId(1);
        lukeLocation.setId(1);
        lukeInv.setId(1);

        signupFacade = new SignupFacade(luke, lukeLocation, lukeInv);
        signupUseCase.handle(signupFacade);
        System.out.println(luke.getLocation());
        assertNotEquals(Optional.empty(), rebelRepoInMem.findById(luke.getId()));
        assertNotEquals(null, luke.getLocation());
        assertNotEquals(null, luke.getInventory());
    }

    @Test
    @DisplayName("should not save anything if rebel not provided")
    void should_not_save_anything_if_rebel_null() {
        luke = null;
        signupFacade = new SignupFacade(luke, lukeLocation, lukeInv);
        try {
            signupUseCase.handle(signupFacade);
        } catch (Exception ignored) {}

        assertTrue(rebelRepoInMem.findAll().isEmpty());
        assertTrue(locationRepoInMem.findAll().isEmpty());
        assertTrue(inventoryRepoInMem.findAll().isEmpty());
    }

    @Test
    @DisplayName("should not save anything if location not provided")
    void should_not_save_anything_if_location_null() {
        lukeLocation = null;
        signupFacade = new SignupFacade(luke, lukeLocation, lukeInv);
        try {
            signupUseCase.handle(signupFacade);
        } catch (Exception ignored) {}

        assertTrue(rebelRepoInMem.findAll().isEmpty());
        assertTrue(locationRepoInMem.findAll().isEmpty());
        assertTrue(inventoryRepoInMem.findAll().isEmpty());
    }

    @Test
    @DisplayName("should not save anything if inventory not provided")
    void should_not_save_anything_if_inventor_null() {
        lukeInv = null;
        signupFacade = new SignupFacade(luke, lukeLocation, lukeInv);

        try {
            signupUseCase.handle(signupFacade);
        } catch (Exception ignored) {}

        assertTrue(rebelRepoInMem.findAll().isEmpty());
        assertTrue(locationRepoInMem.findAll().isEmpty());
        assertTrue(inventoryRepoInMem.findAll().isEmpty());
    }

    @Test
    @DisplayName("should not save anything if latitude not provided")
    void should_not_save_anything_if_latitude_null() {
        lukeLocation.setLocation(null, 23.2, "base");
        signupFacade = new SignupFacade(luke, lukeLocation, lukeInv);

        try {
            signupUseCase.handle(signupFacade);
        } catch (Exception ignored) {}

        assertTrue(rebelRepoInMem.findAll().isEmpty());
        assertTrue(locationRepoInMem.findAll().isEmpty());
        assertTrue(inventoryRepoInMem.findAll().isEmpty());
    }

    @Test
    @DisplayName("should not save anything if longitude not provided")
    void should_not_save_anything_if_longitude_null() {
        lukeLocation.setLocation(23.2, null, "base");
        signupFacade = new SignupFacade(luke, lukeLocation, lukeInv);

        try {
            signupUseCase.handle(signupFacade);
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
        signupFacade = new SignupFacade(luke, lukeLocation, lukeInv);

        Exception e = assertThrows(ResistanceSocialNetworkException.class, () ->
                signupUseCase.handle(signupFacade)
        );

        assertTrue(rebelRepoInMem.findAll().isEmpty());
        assertTrue(locationRepoInMem.findAll().isEmpty());
        assertTrue(inventoryRepoInMem.findAll().isEmpty());
        assertTrue(e.getMessage().contains("must provide item parameters"));
    }
}