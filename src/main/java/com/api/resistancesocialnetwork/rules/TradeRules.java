package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.InventoryRepository;
import com.api.resistancesocialnetwork.repositories.RebelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TradeRules {
    private final InventoryRepository inventoryRepo;
    private final RebelRepository rebelRepo;

    @Autowired
    public TradeRules(InventoryRepository inventoryRepo, RebelRepository rebelRepo) {
        this.inventoryRepo = inventoryRepo;
        this.rebelRepo = rebelRepo;
    }
    public void check(Integer sourceInventoryId, Item sourceTrade, Integer targetInventoryId, Item targetTrade) throws TradeFailureException {
        Inventory sourceInventory = inventoryRepo.findById(sourceInventoryId).orElseThrow(
                () -> new TradeFailureException("source inventory not found")
        );
        Inventory targetInventory = inventoryRepo.findById(targetInventoryId).orElseThrow(
                () -> new TradeFailureException("target inventory not found")
        );

        rebelRepo.findById(sourceInventory.getId()).filter(Rebel::isNotTraitor).orElseThrow(
                () -> new TradeFailureException("source rebel is either a traitor or could not be found")
        );
        rebelRepo.findById(targetInventory.getId()).filter(Rebel::isNotTraitor).orElseThrow(
                () -> new TradeFailureException("target rebel is either a traitor or could not be found")
        );

        Item sourceItem = inventoryRepo.findItemByName(sourceInventoryId, sourceTrade.getName()).orElseThrow(
                () -> new TradeFailureException("no such item source")
        );
        Item targetItem = inventoryRepo.findItemByName(targetInventoryId, targetTrade.getName()).orElseThrow(
                () -> new TradeFailureException("no such item target")
        );

        if (!Objects.equals(sourceItem.getPrice(), targetItem.getPrice()))
            throw new TradeFailureException("points do not match");
    }
}