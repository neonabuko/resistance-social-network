package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.InventoryRepository;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.RebelRepository;
import com.api.resistancesocialnetwork.request.facade.TradeFacade;
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
    private final InventoryRepository inventoryRepo;

    public TradeUseCase(TradeRules tradeRules, RebelRepository rebelRepository, InventoryRepository inventoryRepo) {
        this.tradeRules = tradeRules;
        this.rebelRepo = rebelRepository;
        this.inventoryRepo = inventoryRepo;
    }

    public void handle(TradeFacade tradeFacade) throws TradeFailureException {
        Rebel sourceRebel = rebelRepo.findById(tradeFacade.sourceRebelId()).orElseThrow(
                () -> new TradeFailureException("no such source rebel")
        );
        Rebel targetRebel = rebelRepo.findById(tradeFacade.targetRebelId()).orElseThrow(
                () -> new TradeFailureException("no such target rebel")
        );

        Inventory sourceInventory = sourceRebel.getInventory();
        Inventory targetInventory = targetRebel.getInventory();

        tradeRules.handle(
                sourceRebel,
                targetRebel,
                tradeFacade.sourceItemId(),
                tradeFacade.targetItemId()
        );

        Item sourceItem = sourceInventory.findItemBy(tradeFacade.sourceItemId()).get();
        Item targetItem = targetInventory.findItemBy(tradeFacade.targetItemId()).get();

        sourceInventory.replaceItem(sourceItem, targetItem);
        targetInventory.replaceItem(targetItem, sourceItem);

        inventoryRepo.saveAll(Arrays.asList(sourceInventory, targetInventory));
    }
}