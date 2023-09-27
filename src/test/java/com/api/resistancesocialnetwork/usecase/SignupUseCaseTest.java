package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.formatters.FormatData;
import com.api.resistancesocialnetwork.formatters.FormatEntities;
import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.InventoryRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.LocationRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.RebelRepositoryInMemory;
import com.api.resistancesocialnetwork.request.facade.SignupFacade;
import com.api.resistancesocialnetwork.rules.SignupRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
            new SignupUseCase(
                    rebelRepoInMem,
                    locationRepoInMem,
                    inventoryRepoInMem,
                    signUpRules
            );
    private Rebel luke = new Rebel("luke", 18, "male");
    private Location lukeLocation = new Location(0.2, 21.3, "base/galaxy");
    private final Item doritos = new Item("doritos", 1);
    private Inventory lukeInv = new Inventory(new ArrayList<>(Arrays.asList(doritos)));
    private SignupFacade signupFacade;

    @Test
    void should_save_rebel_with_location_and_inventory() {
        luke.setId(1);
        lukeLocation.setId(1);
        lukeInv.setId(1);

        signupFacade = new SignupFacade(luke, lukeLocation, lukeInv);
        signupUseCase.handle(signupFacade);

        assertNotEquals(Optional.empty(), rebelRepoInMem.findById(luke.getId()));
        assertNotEquals(Optional.empty(), locationRepoInMem.findById(lukeLocation.getId()));
        assertNotEquals(Optional.empty(), inventoryRepoInMem.findById(lukeInv.getId()));
    }

    @Test
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