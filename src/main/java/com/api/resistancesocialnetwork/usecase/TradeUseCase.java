package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.InventoryRepository;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.RebelRepository;
import com.api.resistancesocialnetwork.request.DTO.TradeDTO;
import com.api.resistancesocialnetwork.rules.TradeFailureException;
import com.api.resistancesocialnetwork.rules.TradeRules;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Transactional
public class TradeUseCase {
    private final TradeRules tradeRules;
    private final RebelRepository rebelRepo;

    public TradeUseCase( TradeRules tradeRules,RebelRepository rebelRepository) {
        this.tradeRules = tradeRules;
        this.rebelRepo = rebelRepository;
    }

    public void handle(TradeDTO tradeDTO) throws TradeFailureException {
        Rebel sourceInventory = rebelRepo.findById(tradeDTO.sourceInventoryId()).orElseThrow(
                () -> new TradeFailureException("no such source inventory")
        );
        Rebel targetInventory = rebelRepo.findById(tradeDTO.targetInventoryId()).orElseThrow(
                () -> new TradeFailureException("no such target inventory")
        );

        tradeRules.handle(
                sourceInventory,
                targetInventory,
                tradeDTO.sourceItemId(),
                tradeDTO.targetItemId()
        );

        Item sourceItem = sourceInventory.getInventory().findItemBy(tradeDTO.sourceItemId()).get();
        Item targetItem = targetInventory.getInventory().findItemBy(tradeDTO.targetItemId()).get();

        sourceInventory.getInventory().replaceItem(sourceItem, targetItem);
        targetInventory.getInventory().replaceItem(targetItem, sourceItem);

        rebelRepo.saveAll(Arrays.asList(sourceInventory, targetInventory));
    }
}