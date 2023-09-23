package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.InventoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ItemAveragesPerRebelUseCase {
    private final InventoryRepository inventoryRepository;

    public ItemAveragesPerRebelUseCase(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Map<String, Integer> handle() {
        List<Item> allItemsInInventoryRepo = inventoryRepository.findAll().stream().flatMap(inv -> inv.getItems().stream()).toList();

        Map<String, Integer> itemTotalAmountsMap = new HashMap<>();
        Map<String, Integer> itemAveragesMap = new HashMap<>();

        for (Item item : allItemsInInventoryRepo) {
            int thisItemAmount = itemTotalAmountsMap.getOrDefault(item.getName(), 0) + 1;
            itemTotalAmountsMap.put(item.getName(), thisItemAmount);

            Integer thisItemAverage = thisItemAmount / inventoryRepository.findAll().size();
            itemAveragesMap.put(item.getName(), thisItemAverage);
        }

        return itemAveragesMap;
    }
}
