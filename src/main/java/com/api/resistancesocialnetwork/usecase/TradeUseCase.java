package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.repositories.InventoryRepository;
import com.api.resistancesocialnetwork.repositories.ItemRepository;
import com.api.resistancesocialnetwork.rules.TradeFailureException;
import com.api.resistancesocialnetwork.rules.TradeRules;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeUseCase {
    private final InventoryRepository inventoryRepo;
    private final TradeRules tradeRules;
    private final ItemRepository itemRepo;

    @Autowired
    public TradeUseCase(InventoryRepository inventoryRepo, TradeRules tradeRules, ItemRepository itemRepo) {
        this.inventoryRepo = inventoryRepo;
        this.tradeRules = tradeRules;
        this.itemRepo = itemRepo;
    }

    @Transactional
    public void handle(Integer sourceInventoryId, Item sourceTradeItem,
                       Integer targetInventoryId, Item targetTradeItem) throws TradeFailureException {
        tradeRules.check(sourceInventoryId, sourceTradeItem, targetInventoryId, targetTradeItem);

        Inventory sourceInv = inventoryRepo.findById(sourceInventoryId).get();
        Inventory targetInv = inventoryRepo.findById(targetInventoryId).get();

        sourceInv.findItemByName(sourceTradeItem.getName()).get().setInventory(targetInv);
        targetInv.findItemByName(targetTradeItem.getName()).get().setInventory(sourceInv);

        itemRepo.save(sourceTradeItem);
        itemRepo.save(targetTradeItem);

        inventoryRepo.save(sourceInv);
        inventoryRepo.save(targetInv);
    }
}
