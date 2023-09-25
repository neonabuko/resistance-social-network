package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.InventoryRepository;
import com.api.resistancesocialnetwork.request.DTO.TradeDTO;
import com.api.resistancesocialnetwork.rules.TradeFailureException;
import com.api.resistancesocialnetwork.rules.TradeRules;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TradeUseCase {
    private final InventoryRepository inventoryRepo;
    private final TradeRules tradeRules;

    public TradeUseCase(InventoryRepository inventoryRepo, TradeRules tradeRules) {
        this.inventoryRepo = inventoryRepo;
        this.tradeRules = tradeRules;
    }

    public void handle(TradeDTO tradeDTO) throws TradeFailureException {
        Inventory sourceInventory = inventoryRepo.findById(tradeDTO.sourceInventoryId()).orElseThrow(
                () -> new TradeFailureException("no such source inventory")
        );
        Inventory targetInventory = inventoryRepo.findById(tradeDTO.targetInventoryId()).orElseThrow(
                () -> new TradeFailureException("no such target inventory")
        );

        tradeRules.handle(
                sourceInventory,
                targetInventory,
                tradeDTO.sourceItemId(),
                tradeDTO.targetItemId()
        );

        Item sourceItem = sourceInventory.findItemBy(tradeDTO.sourceItemId()).get();
        Item targetItem = targetInventory.findItemBy(tradeDTO.targetItemId()).get();

        sourceInventory.getItems().remove(sourceItem);
        sourceInventory.getItems().add(targetItem);

        targetInventory.getItems().remove(targetItem);
        targetInventory.getItems().add(sourceItem);

        inventoryRepo.save(sourceInventory);
        inventoryRepo.save(targetInventory);
    }
}