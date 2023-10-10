package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.entity.Inventory;
import com.api.resistancesocialnetwork.entity.Item;
import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.InventoryRepository;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.RebelRepository;
import com.api.resistancesocialnetwork.facade.TradeFacade;
import com.api.resistancesocialnetwork.rules.TradeRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

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

    public void handle(TradeFacade tradeFacadeFacade) throws ResistanceSocialNetworkException {
        TradeFacade tradeFacade = Optional.ofNullable(tradeFacadeFacade).orElseThrow(
                () -> new ResistanceSocialNetworkException("must provide trade parameters")
        );
        Rebel leftRebel = rebelRepo.findById(tradeFacade.leftRebelId()).orElseThrow(
                () -> new ResistanceSocialNetworkException("left rebel not found")
        );
        Rebel rightRebel = rebelRepo.findById(tradeFacade.rightRebelId()).orElseThrow(
                () -> new ResistanceSocialNetworkException("right rebel not found")
        );

        tradeRules.handle(leftRebel, rightRebel, tradeFacade.leftItemId(), tradeFacade.rightItemId());

        Inventory leftInventory = leftRebel.getInventory();
        Inventory rightInventory = rightRebel.getInventory();

        Item leftItem = leftInventory.findItemBy(tradeFacade.leftItemId()).get();
        Item rightItem = rightInventory.findItemBy(tradeFacade.rightItemId()).get();

        leftInventory.replace(leftItem, rightItem);
        rightInventory.replace(rightItem, leftItem);

        inventoryRepo.saveAll(Arrays.asList(leftInventory, rightInventory));
    }
}