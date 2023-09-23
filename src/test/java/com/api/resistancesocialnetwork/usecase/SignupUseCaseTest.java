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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SignupUseCaseTest {
    private final RebelRepositoryInMemory rebelRepoInMem = new RebelRepositoryInMemory();
    private final InventoryRepositoryInMemory inventoryRepoInMem = new InventoryRepositoryInMemory();
    private final LocationRepositoryInMemory locationRepoInMem = new LocationRepositoryInMemory();
    private final ItemRepositoryInMemory itemRepoInMem = new ItemRepositoryInMemory();
    private final SignupUseCase signupUseCase = new SignupUseCase(rebelRepoInMem, locationRepoInMem, inventoryRepoInMem, itemRepoInMem);
    private final Rebel luke = new Rebel("luke", 18, "male");
    private final Rebel leia = new Rebel("leia", 30, "female");
    private final Location lukeLocation = new Location(0.2, 21.3, "base/galaxy");
    private final Location leiaLocation = new Location(0.2, 21.3, "base/galaxy");
    private final Inventory lukeInv = new Inventory(new ArrayList<>(List.of(new Item("doritos", 1))));
    private final Inventory leiaInv = new Inventory(new ArrayList<>(List.of(new Item("water", 2))));

    @BeforeEach
    public void setUp() {
        signupUseCase.handle(luke, lukeLocation, lukeInv);
        signupUseCase.handle(leia, leiaLocation, leiaInv);
    }

    @Test
    void should_save_rebel() {
        luke.setId(1);
        rebelRepoInMem.saveInMem(luke);
        assertNotEquals(Optional.empty(), rebelRepoInMem.findById(luke.getId()));
    }

    @Test
    void should_save_location() {
        lukeLocation.setId(1);
        locationRepoInMem.saveInMem(lukeLocation);
        assertNotEquals(Optional.empty(), locationRepoInMem.findById(lukeLocation.getId()));
    }

    @Test
    void should_save_inventory() {
        lukeInv.setId(1);
        inventoryRepoInMem.saveInMem(lukeInv);
        assertNotEquals(Optional.empty(), inventoryRepoInMem.findById(lukeInv.getId()));
    }

    @Test
    void should_save_items() {
        assertFalse(itemRepoInMem.findAll().isEmpty());
    }
}