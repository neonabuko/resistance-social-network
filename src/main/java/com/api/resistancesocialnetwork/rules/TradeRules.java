package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.entity.Item;
import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.rules.commons.ResistanceException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Transactional
public class TradeRules {
    public void handle(Rebel leftRebel, Rebel rightRebel,
                       Integer leftItemId, Integer rightItemId) throws ResistanceException {
        assert_traders_are_allies(leftRebel, rightRebel);
        Item leftItem = retrieve_item_from_inventory_if_present(leftRebel, leftItemId);
        Item rightItem = retrieve_item_from_inventory_if_present(rightRebel, rightItemId);
        assert_points_match(leftItem.getPrice(), rightItem.getPrice());
    }

    private void assert_traders_are_allies(Rebel leftRebel, Rebel rightRebel) throws ResistanceException {
        if (leftRebel.isTraitor()) throw new ResistanceException("left rebel is a traitor");
        if (rightRebel.isTraitor()) throw new ResistanceException("right rebel is a traitor");
    }

    private Item retrieve_item_from_inventory_if_present(Rebel rebel, Integer tradeItemId) throws ResistanceException {
        return rebel.getInventory().findItemBy(tradeItemId).orElseThrow(
                () -> new ResistanceException("item not found with rebel id " + rebel.getId())
        );
    }

    private void assert_points_match(Integer leftItemPrice, Integer rightItemPrice) throws ResistanceException {
        if (!Objects.equals(leftItemPrice, rightItemPrice)) {
            throw new ResistanceException("points do not match");
        }
    }
}