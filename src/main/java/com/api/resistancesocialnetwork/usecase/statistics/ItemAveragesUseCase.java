package com.api.resistancesocialnetwork.usecase.statistics;

import com.api.resistancesocialnetwork.facade.stats.ItemAverageFacade;
import com.api.resistancesocialnetwork.repository.repositoryinterfaces.InventoryRepository;
import com.api.resistancesocialnetwork.repository.repositoryinterfaces.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ItemAveragesUseCase {
    private final InventoryRepository inventoryRepository;
    private final ItemRepository itemRepository;

    public ItemAveragesUseCase(InventoryRepository inventoryRepository, ItemRepository itemRepository) {
        this.inventoryRepository = inventoryRepository;
        this.itemRepository = itemRepository;
    }

    public ItemAverageFacade handle() {
        var totalItems = itemRepository.findAll().size();
        var totalInventories = inventoryRepository.findAll().size();
        var averageItemsPerInventory = (double) totalItems / totalInventories;

        return new ItemAverageFacade(averageItemsPerInventory);
    }
}
