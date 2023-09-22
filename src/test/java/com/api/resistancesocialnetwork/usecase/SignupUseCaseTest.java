package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.InventoryRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.ItemRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.LocationRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.RebelRepositoryInMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SignupUseCaseTest {
    private final RebelRepositoryInMemory rebelRepo = new RebelRepositoryInMemory();
    private final InventoryRepositoryInMemory inventoryRepo = new InventoryRepositoryInMemory();
    private final LocationRepositoryInMemory locationRepo = new LocationRepositoryInMemory();
    private final ItemRepositoryInMemory itemRepo = new ItemRepositoryInMemory();
    private final SignupUseCase signupUseCase = new SignupUseCase(rebelRepo, locationRepo, inventoryRepo, itemRepo);
    private final Rebel luke = new Rebel("luke", 18, "male");
    private final Rebel leia = new Rebel("leia", 30, "female");
    private final Rebel hanSolo = new Rebel("han solo", 18, "male");
    private final Location lukeLocation = new Location(0.2, 21.3, "base/galaxy");
    private final Location leiaLocation = new Location(0.2, 21.3, "base/galaxy");
    private final Location hanSoloLocation = new Location(24.1, 42.1, "base");
    private final Inventory lukeInv = new Inventory(new ArrayList<>( List.of( new Item("doritos", 1)) ));
    private final Inventory leiaInv = new Inventory(new ArrayList<>( List.of( new Item("water", 2)) ));
    private final Inventory hanSoloInventory = new Inventory(new ArrayList<>(Arrays.asList(( new Item("doritos", 1)),
            new Item("doritos", 1))
            ));

    @BeforeEach
    public void setUp() {
        signupUseCase.handle(luke, lukeLocation, lukeInv);
        signupUseCase.handle(leia, leiaLocation, leiaInv);
    }

    @Test
    void should_save_rebel() {
        assertNotEquals(Optional.empty(), rebelRepo.findById(luke.getId()));
    }

    @Test
    void should_save_location() {
        assertNotEquals(Optional.empty(), locationRepo.findById(lukeLocation.getId()));
    }

    @Test
    void should_save_inventory() {
        assertNotEquals(Optional.empty(), inventoryRepo.findById(lukeInv.getId()));
    }

    @Test
    void should_save_items() {
        assertFalse(itemRepo.findAll().isEmpty());
    }
}