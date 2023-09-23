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

        Inventory sourceInv = inventoriesInTrade.get(0);
        Inventory targetInv = inventoriesInTrade.get(1);

        Item actualSourceItem = sourceInv.findItemByName(sourceTradeItemName).get();
        Item actualTargetItem = targetInv.findItemByName(targetTradeItemName).get();

        sourceInv.getItems().set(sourceInv.getItems().indexOf(actualSourceItem), actualTargetItem);
        targetInv.getItems().set(targetInv.getItems().indexOf(actualTargetItem), actualSourceItem);

        actualSourceItem.setInventory(targetInv);
        actualTargetItem.setInventory(sourceInv);

        itemRepo.saveAll(Arrays.asList(actualSourceItem, actualTargetItem));
        inventoryRepo.save(sourceInv);
        inventoryRepo.save(targetInv);
    }
}
