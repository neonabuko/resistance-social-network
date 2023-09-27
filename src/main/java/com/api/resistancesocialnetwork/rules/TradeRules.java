package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Transactional
public class TradeRules {
    public void handle(Rebel leftRebel, Rebel rightRebel,
                       Integer leftItemId, Integer rightItemId) throws ResistanceSocialNetworkException {
        assert_traders_are_allies(leftRebel, rightRebel);
        Item leftItem = assert_inventory_has_item(leftRebel, leftItemId);
        Item rightItem = assert_inventory_has_item(rightRebel, rightItemId);
        assert_points_match(leftItem.getPrice(), rightItem.getPrice());
    }

    private void assert_traders_are_allies(Rebel leftRebel, Rebel rightRebel) throws ResistanceSocialNetworkException {
        if (leftRebel.isTraitor()) throw new ResistanceSocialNetworkException("left rebel is a traitor");
        if (rightRebel.isTraitor()) throw new ResistanceSocialNetworkException("right rebel is a traitor");
    }

    private Item assert_inventory_has_item(Rebel rebel, Integer tradeItemId) throws ResistanceSocialNetworkException {
        return rebel.getInventory().findItemBy(tradeItemId).orElseThrow(
                () -> new ResistanceSocialNetworkException("item not found with rebel id " + rebel.getId())
        );
    }

    private void assert_points_match(Integer leftItemPrice, Integer rightItemPrice) throws ResistanceSocialNetworkException {
        if (!Objects.equals(leftItemPrice, rightItemPrice)) {
            throw new ResistanceSocialNetworkException("points do not match");
        }
    }
}