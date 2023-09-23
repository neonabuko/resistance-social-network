package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.InventoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class TradeRules {
    private final InventoryRepository inventoryRepo;

    public TradeRules(InventoryRepository inventoryRepo) {
        this.inventoryRepo = inventoryRepo;
    }

    public List<Inventory> check(Integer sourceInventoryId,
                                 String sourceTradeItemName,
                                 Integer targetInventoryId,
                                 String targetTradeItemName) throws TradeFailureException {

        Optional<Integer> optionalSourceInventoryId = Optional.ofNullable(sourceInventoryId);
        Inventory sourceInventory = inventoryRepo.findById(optionalSourceInventoryId.orElseThrow(
                () -> new TradeFailureException("must provide source inventory id"))).orElseThrow(
                        () -> new TradeFailureException("source inventory not found")
        );

        Optional<Integer> optionalTargetInventoryId = Optional.ofNullable(targetInventoryId);
        Inventory targetInventory = inventoryRepo.findById(optionalTargetInventoryId.orElseThrow(
                () -> new TradeFailureException("must provide target inventory id"))).orElseThrow(
                        () -> new TradeFailureException("target inventory not found")
        );

        if (sourceInventory.getRebel().isTraitor()) throw new TradeFailureException("source rebel is a traitor");

        if (targetInventory.getRebel().isTraitor()) throw new TradeFailureException("target rebel is a traitor");

        Item sourceItem = sourceInventory.findItemByName(sourceTradeItemName).orElseThrow(
                () -> new TradeFailureException("source item not found")
        );
        Item targetItem = targetInventory.findItemByName(targetTradeItemName).orElseThrow(
                () -> new TradeFailureException("target item not found")
        );

        if (!Objects.equals(sourceItem.getPrice(), targetItem.getPrice())) {
            throw new TradeFailureException("points do not match");
        }

        return new ArrayList<>(Arrays.asList(sourceInventory, targetInventory));
    }
}