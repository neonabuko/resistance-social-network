package com.api.resistancesocialnetwork.usecase.statistics;

import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.InventoryRepository;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ItemAveragesPerRebelUseCase {
    private final InventoryRepository inventoryRepository;
    private final ItemRepository itemRepository;

    public ItemAveragesPerRebelUseCase(InventoryRepository inventoryRepository, ItemRepository itemRepository) {
        this.inventoryRepository = inventoryRepository;
        this.itemRepository = itemRepository;
    }

    public Map<String, Double> handle() {
        List<Item> allItems = itemRepository.findAll();

        Map<String, Double> items_with_total_amounts_Map = new HashMap<>();
        Map<String, Double> items_with_average_amounts_Map = new HashMap<>();

        for (Item item : allItems) {
            double thisAmount = items_with_total_amounts_Map.getOrDefault(item.getName(), 0.0) + 1;
            items_with_total_amounts_Map.put(item.getName(), thisAmount);
            double thisAverageAmount = thisAmount / inventoryRepository.findAll().size();
            items_with_average_amounts_Map.put(item.getName(), Math.round(thisAverageAmount * 10.0) / 10.0);
        }

        return items_with_average_amounts_Map;
    }
}
