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

    public Map<String, Double> handle() {
        List<Item> allItemsInInventoryRepo = inventoryRepository.findAll().stream().flatMap(inv -> inv.getItems().stream()).toList();

        Map<String, Double> itemTotalAmountsMap = new HashMap<>();
        Map<String, Double> itemAveragesMap = new HashMap<>();

        for (Item item : allItemsInInventoryRepo) {
            double thisItemAmount = itemTotalAmountsMap.getOrDefault(item.getName(), 0.0) + 1;
            itemTotalAmountsMap.put(item.getName(), thisItemAmount);

            double thisItemAverage = thisItemAmount / inventoryRepository.findAll().size();
            itemAveragesMap.put(item.getName(), Math.round(thisItemAverage * 10.0) / 10.0);
        }

        return itemAveragesMap;
    }
}
