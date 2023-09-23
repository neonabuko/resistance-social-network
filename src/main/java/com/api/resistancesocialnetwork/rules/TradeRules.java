package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.InventoryRepository;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.RebelRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class TradeRules {
    private final InventoryRepository inventoryRepo;
    private final RebelRepository rebelRepo;

    public TradeRules(InventoryRepository inventoryRepo, RebelRepository rebelRepo) {
        this.inventoryRepo = inventoryRepo;
        this.rebelRepo = rebelRepo;
    }

    public List<Inventory> check(Integer sourceInventoryId, Item sourceTradeItem, Integer targetInventoryId, Item targetTradeItem) throws TradeFailureException {
        Optional<Integer> optionalSourceInventoryId = Optional.of(sourceInventoryId);

        Inventory sourceInventory = inventoryRepo.findById(optionalSourceInventoryId.orElseThrow(
                () -> new TradeFailureException("must provide source inventory id"))).orElseThrow(
                        () -> new TradeFailureException("source inventory not found")
        );

        Optional<Integer> optionalTargetInventoryId = Optional.of(targetInventoryId);
        Inventory targetInventory = inventoryRepo.findById(optionalTargetInventoryId.orElseThrow(
                () -> new TradeFailureException("must provide target inventory id"))).orElseThrow(
                        () -> new TradeFailureException("target inventory not found")
        );

        rebelRepo.findById(sourceInventory.getRebel().getId()).filter(Rebel::isNotTraitor).orElseThrow(
                () -> new TradeFailureException("source rebel is a traitor")
        );
        rebelRepo.findById(targetInventory.getRebel().getId()).filter(Rebel::isNotTraitor).orElseThrow(
                () -> new TradeFailureException("target rebel is a traitor")
        );

        Item sourceItem = inventoryRepo.findItemByName(sourceInventoryId, sourceTradeItem.getName()).orElseThrow(
                () -> new TradeFailureException("no such item source")
        );
        Item targetItem = inventoryRepo.findItemByName(targetInventoryId, targetTradeItem.getName()).orElseThrow(
                () -> new TradeFailureException("no such item target")
        );

        if (!Objects.equals(sourceItem.getPrice(), targetItem.getPrice())) {
            throw new TradeFailureException("points do not match");
        }

        return new ArrayList<>(Arrays.asList(sourceInventory, targetInventory));
    }
}