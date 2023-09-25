package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.InventoryRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.ItemRepositoryInMemory;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemAveragesPerRebelUseCaseTest {
    private final InventoryRepositoryInMemory inventoryRepoInMem = new InventoryRepositoryInMemory();
    private final ItemRepositoryInMemory itemRepositoryInMem = new ItemRepositoryInMemory();
    private final ItemAveragesPerRebelUseCase itemAveragesPerRebelUseCase = new ItemAveragesPerRebelUseCase(inventoryRepoInMem, itemRepositoryInMem);

    private final Item doritos = new Item("doritos", 1);
    private final Item water = new Item("water", 2);
    private final Item fandango = new Item("fandango", 1);
    private final Inventory lukeInv = new Inventory(Arrays.asList(doritos, water, fandango));
    private final Inventory leiaInv = new Inventory(Arrays.asList(doritos, water));

    @Test
    void should_return_hashMap_with_correct_averages() {
        itemRepositoryInMem.save(doritos);
        itemRepositoryInMem.save(water);
        itemRepositoryInMem.save(fandango);
        itemRepositoryInMem.save(doritos);
        itemRepositoryInMem.save(water);

        inventoryRepoInMem.save(lukeInv);
        inventoryRepoInMem.save(leiaInv);

        Map<String, Double> expectedAverages = new HashMap<>();
        expectedAverages.put("doritos", 1.0);
        expectedAverages.put("water", 1.0);
        expectedAverages.put("fandango", 0.5);
        Map<String, Double> actualAverages = itemAveragesPerRebelUseCase.handle();

        assertEquals(expectedAverages.toString(), actualAverages.toString());
    }
}