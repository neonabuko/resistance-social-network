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
    public void handle(Rebel rebelLeft, Rebel rebelRight, Integer itemIdLeft, Integer itemIdRight) throws ResistanceException {
        assert_traders_are_allies(rebelLeft, rebelRight);
        Item leftItem = get_item_from_inventory_if_present(rebelLeft, itemIdLeft);
        Item rightItem = get_item_from_inventory_if_present(rebelRight, itemIdRight);
        assert_points_match(leftItem.getPrice(), rightItem.getPrice());
        assert_not_self_trade(rebelLeft, rebelRight);
    }

    private void assert_traders_are_allies(Rebel rebelLeft, Rebel rebelRight) throws ResistanceException {
        if (rebelLeft.isTraitor()) throw new ResistanceException("left rebel is a traitor");
        if (rebelRight.isTraitor()) throw new ResistanceException("right rebel is a traitor");
    }

    private Item get_item_from_inventory_if_present(Rebel rebel, Integer itemId) throws ResistanceException {
        return rebel.getInventory().findItemBy(itemId).orElseThrow(
                () -> new ResistanceException("item not found with rebel id " + rebel.getId())
        );
    }

    private void assert_points_match(Integer leftItemPrice, Integer rightItemPrice) throws ResistanceException {
        if (!Objects.equals(leftItemPrice, rightItemPrice)) {
            throw new ResistanceException("points do not match");
        }
    }

    private void assert_not_self_trade(Rebel rebelLeft, Rebel rebelRight) {
        if (Objects.equals(rebelLeft.getId(), rebelRight.getId())) throw new ResistanceException("cannot trade with yourself");
    }
}