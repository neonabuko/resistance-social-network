package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.entity.Inventory;
import com.api.resistancesocialnetwork.entity.Item;
import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.facade.TradeFacade;
import com.api.resistancesocialnetwork.repository.repositoryinterfaces.InventoryRepository;
import com.api.resistancesocialnetwork.repository.repositoryinterfaces.RebelRepository;
import com.api.resistancesocialnetwork.rules.TradeRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceException;
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

    public void handle(TradeFacade tradeFacadeFacade) throws ResistanceException {
        TradeFacade tradeFacade = Optional.ofNullable(tradeFacadeFacade).orElseThrow(
                () -> new ResistanceException("must provide trade parameters")
        );
        Rebel rebelLeft = rebelRepo.findById(tradeFacade.leftRebelId()).orElseThrow(
                () -> new ResistanceException("left rebel not found")
        );
        Rebel rebelRight = rebelRepo.findById(tradeFacade.rightRebelId()).orElseThrow(
                () -> new ResistanceException("right rebel not found")
        );

        tradeRules.handle(rebelLeft, rebelRight, tradeFacade.leftItemId(), tradeFacade.rightItemId());

        Inventory leftInventory = rebelLeft.getInventory();
        Inventory rightInventory = rebelRight.getInventory();

        Item itemLeft = leftInventory.findItemBy(tradeFacade.leftItemId()).get();
        Item itemRight = rightInventory.findItemBy(tradeFacade.rightItemId()).get();

        leftInventory.replace(itemLeft, itemRight);
        rightInventory.replace(itemRight, itemLeft);

        inventoryRepo.saveAll(Arrays.asList(leftInventory, rightInventory));
    }
}