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

        check_If_Traders_Are_Allies(sourceInventory.getRebel(), targetInventory.getRebel());

        check_If_Source_Item_Exists(sourceInventory, sourceTradeItemId);

        check_If_Target_Item_Exists(targetInventory, targetTradeItemId);

        check_If_Points_Match(
                sourceInventory.findItemBy(sourceTradeItemId).get().getPrice(),
                targetInventory.findItemBy(targetTradeItemId).get().getPrice()
        );
    }

    private void check_If_Traders_Are_Allies(Rebel sourceRebel, Rebel targetRebel) throws TradeFailureException {
        if (sourceRebel.isTraitor()) throw new TradeFailureException("source rebel is a traitor");

        if (targetRebel.isTraitor()) throw new TradeFailureException("target rebel is a traitor");
    }

    private void check_If_Source_Item_Exists(Inventory sourceInventory, Integer sourceTradeItemId) throws TradeFailureException {
        sourceInventory.findItemBy(sourceTradeItemId).orElseThrow(
                () -> new TradeFailureException("source item not found")
        );
    }

    private void check_If_Target_Item_Exists(Inventory targetInventory, Integer targetTradeItemId) throws TradeFailureException {
        targetInventory.findItemBy(targetTradeItemId).orElseThrow(
                () -> new TradeFailureException("target item not found")
        );
    }

    private void check_If_Points_Match(Integer sourceItemPrice, Integer targetItemPrice) throws TradeFailureException {
        if (!Objects.equals(sourceItemPrice, targetItemPrice)) {
            throw new TradeFailureException("points do not match");
        }
    }
}