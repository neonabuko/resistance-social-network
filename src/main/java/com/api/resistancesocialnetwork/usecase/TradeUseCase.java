package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.InventoryRepository;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.ItemRepository;
import com.api.resistancesocialnetwork.request.DTO.TradeDTO;
import com.api.resistancesocialnetwork.rules.TradeFailureException;
import com.api.resistancesocialnetwork.rules.TradeRules;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Arrays;

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
                tradeDTO.sourceTradeItemId(),
                tradeDTO.targetTradeItemId()
        );

        Item sourceItem = sourceInventory.findItemBy(tradeDTO.sourceTradeItemId()).get();
        Item targetItem = targetInventory.findItemBy(tradeDTO.targetTradeItemId()).get();

        sourceInventory.getItems().set(sourceInventory.getItems().indexOf(sourceItem), targetItem);
        targetInventory.getItems().set(targetInventory.getItems().indexOf(targetItem), sourceItem);

        itemRepo.saveAll(Arrays.asList(sourceItem, targetItem));
        inventoryRepo.save(sourceInventory);
        inventoryRepo.save(targetInventory);
    }
}