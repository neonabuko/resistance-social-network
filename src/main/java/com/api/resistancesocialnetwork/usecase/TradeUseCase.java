package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.InventoryRepository;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.ItemRepository;
import com.api.resistancesocialnetwork.rules.TradeFailureException;
import com.api.resistancesocialnetwork.rules.TradeRules;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TradeUseCase {
    private final InventoryRepository inventoryRepo;
    private final TradeRules tradeRules;
    private final ItemRepository itemRepo;

    public TradeUseCase(InventoryRepository inventoryRepo, TradeRules tradeRules, ItemRepository itemRepo) {
        this.inventoryRepo = inventoryRepo;
        this.tradeRules = tradeRules;
        this.itemRepo = itemRepo;
    }

    @Transactional
    public void handle(Integer sourceInventoryId, Item sourceTradeItem,
                       Integer targetInventoryId, Item targetTradeItem) throws TradeFailureException {

        tradeRules.check(sourceInventoryId, sourceTradeItem, targetInventoryId, targetTradeItem);

        Inventory sourceInv = inventoryRepo.findById(sourceInventoryId).orElseThrow(
                () -> new TradeFailureException("source inventory not found"));
        Inventory targetInv = inventoryRepo.findById(targetInventoryId).orElseThrow(
                () -> new TradeFailureException("target inventory not found"));

        itemRepo.setOwnerInventory(targetTradeItem, sourceInv);
        itemRepo.setOwnerInventory(sourceTradeItem, targetInv);

        itemRepo.save(sourceTradeItem);
        itemRepo.save(targetTradeItem);

        sourceInv.getItems().set(sourceInv.getItems().indexOf(sourceTradeItem), targetTradeItem);
        targetInv.getItems().set(targetInv.getItems().indexOf(targetTradeItem), sourceTradeItem);

        inventoryRepo.save(sourceInv);
        inventoryRepo.save(targetInv);
    }
}
