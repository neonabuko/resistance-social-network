package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.model.Rebel;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Transactional
public class TradeRules {
    public void handle(Rebel sourceInventory, Rebel targetInventory,
                       Integer sourceItemId, Integer targetItemId) throws TradeFailureException {


        assert_traders_are_allies(sourceInventory.getInventory().getRebel(), targetInventory.getInventory().getRebel());
        assert_inventory_has_item(sourceInventory, sourceItemId);
        assert_inventory_has_item(targetInventory.getInventory().getRebel(), targetItemId);

        Integer sourceItemPrice = sourceInventory.getInventory().findItemBy(sourceItemId).get().getPrice();
        Integer targetItemPrice = targetInventory.getInventory().findItemBy(targetItemId).get().getPrice();

        assert_points_match(sourceItemPrice, targetItemPrice);
    }

    private void assert_traders_are_allies(Rebel sourceRebel, Rebel targetRebel) throws TradeFailureException {
        if (sourceRebel.isTraitor()) throw new TradeFailureException("source rebel is a traitor");
        if (targetRebel.isTraitor()) throw new TradeFailureException("target rebel is a traitor");
    }

    private void assert_inventory_has_item(Rebel inventory, Integer tradeItemId) throws TradeFailureException {
        inventory.getInventory().findItemBy(tradeItemId).orElseThrow(
                () -> new TradeFailureException("Inventory id " + inventory.getId() + ": item not found")
        );
    }

    private void assert_points_match(Integer sourceItemPrice, Integer targetItemPrice) throws TradeFailureException {
        if (!Objects.equals(sourceItemPrice, targetItemPrice)) {
            throw new TradeFailureException("points do not match");
        }
    }
}