package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.InventoryRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.ItemRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.LocationRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.RebelRepositoryInMemory;
import com.api.resistancesocialnetwork.request.DTO.SignupDTO;
import com.api.resistancesocialnetwork.formatters.FormatData;
import com.api.resistancesocialnetwork.formatters.FormatEntities;
import com.api.resistancesocialnetwork.rules.SignupRules;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SignupUseCaseTest {
    private final RebelRepositoryInMemory rebelRepoInMem = new RebelRepositoryInMemory();
    private final InventoryRepositoryInMemory inventoryRepoInMem = new InventoryRepositoryInMemory();
    private final LocationRepositoryInMemory locationRepoInMem = new LocationRepositoryInMemory();
    private final ItemRepositoryInMemory itemRepoInMem = new ItemRepositoryInMemory();
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
    private SignupDTO signupDTO;

    @Test
    void should_save_rebel_with_location_and_inventory() {
        signupDTO = new SignupDTO(luke, lukeLocation, lukeInv);
        signupUseCase.handle(signupDTO);

        assertNotEquals(Optional.empty(), rebelRepoInMem.findById(luke.getId()));
        assertNotEquals(Optional.empty(), locationRepoInMem.findById(lukeLocation.getId()));
        assertNotEquals(Optional.empty(), inventoryRepoInMem.findById(lukeInv.getId()));
        assertFalse(itemRepoInMem.findAll().isEmpty());
    }

    @Test
    void should_not_save_anything_if_rebel_null() {
        luke = null;
        signupDTO = new SignupDTO(luke, lukeLocation, lukeInv);
        try {
            signupUseCase.handle(signupDTO);
        } catch (Exception ignored) {}

        assertTrue(rebelRepoInMem.findAll().isEmpty());
        assertTrue(locationRepoInMem.findAll().isEmpty());
        assertTrue(inventoryRepoInMem.findAll().isEmpty());
    }

    @Test
    void should_not_save_anything_if_location_null() {
        lukeLocation = null;
        signupDTO = new SignupDTO(luke, lukeLocation, lukeInv);
        try {
            signupUseCase.handle(signupDTO);
        } catch (Exception ignored) {}

        assertTrue(rebelRepoInMem.findAll().isEmpty());
        assertTrue(locationRepoInMem.findAll().isEmpty());
        assertTrue(inventoryRepoInMem.findAll().isEmpty());
    }

    @Test
    void should_not_save_anything_if_inventor_null() {
        lukeInv = null;
        signupDTO = new SignupDTO(luke, lukeLocation, lukeInv);

        try {
            signupUseCase.handle(signupDTO);
        } catch (Exception ignored) {}

        assertTrue(rebelRepoInMem.findAll().isEmpty());
        assertTrue(locationRepoInMem.findAll().isEmpty());
        assertTrue(inventoryRepoInMem.findAll().isEmpty());
    }

    @Test
    void should_not_save_anything_if_latitude_null() {
        lukeLocation.setLocation(null, 23.2, "base");
        signupDTO = new SignupDTO(luke, lukeLocation, lukeInv);

        try {
            signupUseCase.handle(signupDTO);
        } catch (Exception ignored) {}

        assertTrue(rebelRepoInMem.findAll().isEmpty());
        assertTrue(locationRepoInMem.findAll().isEmpty());
        assertTrue(inventoryRepoInMem.findAll().isEmpty());
    }

    @Test
    void should_not_save_anything_if_longitude_null() {
        lukeLocation.setLocation(23.2, null, "base");
        signupDTO = new SignupDTO(luke, lukeLocation, lukeInv);

        try {
            signupUseCase.handle(signupDTO);
        } catch (Exception ignored) {}

        assertTrue(rebelRepoInMem.findAll().isEmpty());
        assertTrue(locationRepoInMem.findAll().isEmpty());
        assertTrue(inventoryRepoInMem.findAll().isEmpty());
    }

    @Test
    void should_not_save_anything_if_item_null() {
        Item nullItem = null;
        lukeInv.setItems(Arrays.asList(nullItem));
        signupDTO = new SignupDTO(luke, lukeLocation, lukeInv);

        Exception e = assertThrows(IllegalStateException.class, () ->
                signupUseCase.handle(signupDTO)
        );

        assertTrue(rebelRepoInMem.findAll().isEmpty());
        assertTrue(locationRepoInMem.findAll().isEmpty());
        assertTrue(inventoryRepoInMem.findAll().isEmpty());
        assertTrue(e.getMessage().contains("must provide at least one item"));
    }
}