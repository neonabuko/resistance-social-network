package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemAveragesPerRebelUseCase {
    private final InventoryRepository inventoryRepository;

    @Autowired
    public ItemAveragesPerRebelUseCase(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
    public Map<String, Integer> handle() {
        List<Item> allItems = inventoryRepository.findAll().stream().flatMap(inv -> inv.getItems().stream()).toList();

        Map<String, Integer> totalEach = new HashMap<>();

        Map<String, Integer> averagesAllItems = new HashMap<>();

        for (Item i : allItems) {
            int totalAmountItem = totalEach.getOrDefault(i.getName(), 0) + 1;
            totalEach.put(i.getName(), totalAmountItem);
            Integer average = totalAmountItem / inventoryRepository.findAll().size();
            averagesAllItems.put(i.getName(), average);
        }
        return averagesAllItems;
    }
}
