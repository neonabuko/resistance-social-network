package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Rebel;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Transactional
public class TradeRules {
    public void handle(Inventory sourceInventory, Inventory targetInventory,
                       Integer sourceTradeItemId, Integer targetTradeItemId) throws TradeFailureException {

        assert_traders_are_allies(sourceInventory.getRebel(), targetInventory.getRebel());
        assert_inventory_has_item(sourceInventory, sourceTradeItemId);
        assert_inventory_has_item(targetInventory, targetTradeItemId);
        assert_points_match(
                sourceInventory.findItemBy(sourceTradeItemId).get().getPrice(),
                targetInventory.findItemBy(targetTradeItemId).get().getPrice()
        );
    }

    private void assert_traders_are_allies(Rebel sourceRebel, Rebel targetRebel) throws TradeFailureException {
        if (sourceRebel.isTraitor()) throw new TradeFailureException("source rebel is a traitor");
        if (targetRebel.isTraitor()) throw new TradeFailureException("target rebel is a traitor");
    }

    private void assert_inventory_has_item(Inventory inventory, Integer tradeItemId) throws TradeFailureException {
        inventory.findItemBy(tradeItemId).orElseThrow(
                () -> new TradeFailureException("Inventory id " + inventory.getId() + ": item not found")
        );
    }

    private void assert_points_match(Integer sourceItemPrice, Integer targetItemPrice) throws TradeFailureException {
        if (!Objects.equals(sourceItemPrice, targetItemPrice)) {
            throw new TradeFailureException("points do not match");
        }
    }
}