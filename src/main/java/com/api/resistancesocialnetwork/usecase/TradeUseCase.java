package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.repositories.InventoryRepository;
import com.api.resistancesocialnetwork.rules.TradeFailureException;
import com.api.resistancesocialnetwork.rules.TradeRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeUseCase {
    private final InventoryRepository inventoryRepo;
    private final TradeRules tradeRules;

    @Autowired
    public TradeUseCase(InventoryRepository inventoryRepo, TradeRules tradeRules) {
        this.inventoryRepo = inventoryRepo;
        this.tradeRules = tradeRules;
    }

    public void handle(Integer sourceInventoryId, Item sourceTradeItem, Integer targetInventoryId, Item targetTradeItem) throws TradeFailureException {
        tradeRules.check(sourceInventoryId, sourceTradeItem, targetInventoryId, targetTradeItem);

        Inventory sourceInv = inventoryRepo.findById(sourceInventoryId).get();
        Inventory targetInv = inventoryRepo.findById(targetInventoryId).get();

        sourceInv.getItems().add(targetTradeItem);
        sourceInv.getItems().remove(sourceTradeItem);
        targetInv.getItems().add(sourceTradeItem);
        targetInv.getItems().remove(targetTradeItem);

        inventoryRepo.save(sourceInv);
        inventoryRepo.save(targetInv);
    }
}
