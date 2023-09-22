package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.InventoryRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.ItemRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.RebelRepositoryInMemory;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemAveragesPerRebelUseCaseTest {
    private final InventoryRepositoryInMemory inventoryRepo = new InventoryRepositoryInMemory();
    private final RebelRepositoryInMemory rebelRepo = new RebelRepositoryInMemory();
    private final ItemRepositoryInMemory itemRepo = new ItemRepositoryInMemory();

    private final ItemAveragesPerRebelUseCase itemAveragesPerRebelUseCase = new ItemAveragesPerRebelUseCase(inventoryRepo);

    private final Rebel luke = new Rebel("luke", 18, "male");
    private final Rebel leia = new Rebel("leia", 30, "female");
    private final Item doritos = new Item("doritos", 1);
    private final Item water = new Item("water", 2);
    private final Item fandango = new Item("fandango", 1);
    private final Inventory lukeInv = new Inventory(Arrays.asList(doritos, water, fandango));
    private final Inventory leiaInv = new Inventory(Arrays.asList(doritos, water, fandango));

    @Test
    void should_return_averages_hashMap() {
        rebelRepo.save(luke);
        rebelRepo.save(leia);

        lukeInv.setRebel(luke);
        leiaInv.setRebel(leia);

        doritos.setInventory(lukeInv);
        water.setInventory(leiaInv);
        fandango.setInventory(lukeInv);

        inventoryRepo.save(lukeInv);
        inventoryRepo.save(leiaInv);

        itemRepo.save(doritos);
        itemRepo.save(water);
        itemRepo.save(fandango);

        Map<String, Integer> expectedAverages = new HashMap<>();
        expectedAverages.put("doritos", 1);
        expectedAverages.put("water", 1);
        expectedAverages.put("fandango",1);
        Map<String, Integer> actualAverages = itemAveragesPerRebelUseCase.handle();
        assertEquals(expectedAverages.toString(), actualAverages.toString());
    }
}