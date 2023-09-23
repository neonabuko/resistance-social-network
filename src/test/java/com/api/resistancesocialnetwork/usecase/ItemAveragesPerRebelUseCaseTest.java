package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.InventoryRepositoryInMemory;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemAveragesPerRebelUseCaseTest {
    private final InventoryRepositoryInMemory inventoryRepo = new InventoryRepositoryInMemory();
    private final ItemAveragesPerRebelUseCase itemAveragesPerRebelUseCase = new ItemAveragesPerRebelUseCase(inventoryRepo);

    private final Item doritos = new Item("doritos", 1);
    private final Item water = new Item("water", 2);
    private final Item fandango = new Item("fandango", 1);
    private final Inventory lukeInv = new Inventory(Arrays.asList(doritos, water, fandango));
    private final Inventory leiaInv = new Inventory(Arrays.asList(doritos, water, fandango));

    @Test
    void should_return_hashMap_with_correct_averages() {
        inventoryRepo.saveInMem(lukeInv);
        inventoryRepo.saveInMem(leiaInv);

        Map<String, Double> expectedAverages = new HashMap<>();
        expectedAverages.put("doritos", 1.0);
        expectedAverages.put("water", 1.0);
        expectedAverages.put("fandango", 1.0);
        Map<String, Double> actualAverages = itemAveragesPerRebelUseCase.handle();

        assertEquals(expectedAverages.toString(), actualAverages.toString());
    }
}