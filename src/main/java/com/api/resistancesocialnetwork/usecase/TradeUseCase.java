package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.InventoryRepository;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.ItemRepository;
import com.api.resistancesocialnetwork.rules.TradeFailureException;
import com.api.resistancesocialnetwork.rules.TradeRules;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class TradeUseCase {
    private final InventoryRepository inventoryRepo;
    private final TradeRules tradeRules;
    private final ItemRepository itemRepo;

    public TradeUseCase(InventoryRepository inventoryRepo, TradeRules tradeRules, ItemRepository itemRepo) {
        this.inventoryRepo = inventoryRepo;
        this.tradeRules = tradeRules;
        this.itemRepo = itemRepo;
    }

    public void handle(Integer sourceInventoryId,
                       String sourceTradeItemName,
                       Integer targetInventoryId,
                       String targetTradeItemName) throws TradeFailureException {
        List<Inventory> inventoriesInTrade = tradeRules.check(sourceInventoryId, sourceTradeItemName, targetInventoryId, targetTradeItemName);

        Inventory sourceInventory = inventoriesInTrade.get(0);
        Inventory targetInventory = inventoriesInTrade.get(1);

        Item sourceItem = sourceInventory.findItemByName(sourceTradeItemName).get();
        Item targetItem = targetInventory.findItemByName(targetTradeItemName).get();

        sourceInventory.getItems().set(sourceInventory.getItems().indexOf(sourceItem), targetItem);
        targetInventory.getItems().set(targetInventory.getItems().indexOf(targetItem), sourceItem);

        sourceItem.setInventory(targetInventory);
        targetItem.setInventory(sourceInventory);

        itemRepo.saveAll(Arrays.asList(sourceItem, targetItem));
        inventoryRepo.save(sourceInventory);
        inventoryRepo.save(targetInventory);
    }
}
