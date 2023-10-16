package com.api.resistancesocialnetwork.unit.usecase;

import com.api.resistancesocialnetwork.entity.Inventory;
import com.api.resistancesocialnetwork.entity.Item;
import com.api.resistancesocialnetwork.repository.repositoriesinmemory.InventoryRepositoryInMemory;
import com.api.resistancesocialnetwork.repository.repositoriesinmemory.ItemRepositoryInMemory;
import com.api.resistancesocialnetwork.usecase.statistics.ItemAveragesUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemAveragesUseCaseTest {
    private final InventoryRepositoryInMemory inventoryRepoInMem = new InventoryRepositoryInMemory();
    private final ItemRepositoryInMemory itemRepositoryInMem = new ItemRepositoryInMemory();
    private final ItemAveragesUseCase itemAveragesUseCase = new ItemAveragesUseCase(inventoryRepoInMem, itemRepositoryInMem);

    private final Item doritos = new Item("doritos", 1);
    private final Item water = new Item("water", 2);
    private final Item fandango = new Item("fandango", 1);
    private final Inventory lukeInv = new Inventory(Arrays.asList(doritos, water, fandango));
    private final Inventory leiaInv = new Inventory(Arrays.asList(doritos, water));

    @Test
    @DisplayName("should contain all items in repository, associated with their respective average amounts per rebel")
    void should_return_hashMap_with_correct_averages() {
        itemRepositoryInMem.saveAll(Arrays.asList(doritos, water, fandango, doritos, water));
        inventoryRepoInMem.saveAll(Arrays.asList(lukeInv, leiaInv));

        Map<String, Double> expectedAverages = new HashMap<>();
        expectedAverages.put("doritos", 1.0);
        expectedAverages.put("water", 1.0);
        expectedAverages.put("fandango", 0.5);

        Map<String, Double> actualAverages = itemAveragesUseCase.handle();

        assertEquals(expectedAverages, actualAverages);
    }
}