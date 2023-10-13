package com.api.resistancesocialnetwork.unit.rules;

import com.api.resistancesocialnetwork.entity.Inventory;
import com.api.resistancesocialnetwork.entity.Item;
import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.InventoryRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.ItemRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.RebelRepositoryInMemory;
import com.api.resistancesocialnetwork.rules.TradeRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TradeRulesTest {
    private final RebelRepositoryInMemory rebelRepoInMem = new RebelRepositoryInMemory();
    private final InventoryRepositoryInMemory inventoryRepoInMem = new InventoryRepositoryInMemory();
    private final ItemRepositoryInMemory itemRepositoryInMemory = new ItemRepositoryInMemory();
    private final Item leftItem = new Item("doritos", 1);
    private final Item rightItem = new Item("water", 2);
    private final Inventory leftInventory = new Inventory(List.of(leftItem));
    private final Inventory rightInventory = new Inventory(List.of(rightItem));
    private final TradeRules tradeRules = new TradeRules();
    private Rebel leftRebel;
    private Rebel rightRebel;

    @BeforeEach
    void setUp() {
        leftRebel = new Rebel("luke", 18, "male");
        rightRebel = new Rebel("leia", 30, "female");
        leftRebel.setId(1);
        rightRebel.setId(2);
        rebelRepoInMem.saveAll(Arrays.asList(leftRebel, rightRebel));

        leftInventory.setId(1);
        rightInventory.setId(2);
        leftRebel.setInventory(leftInventory);
        rightRebel.setInventory(rightInventory);

        inventoryRepoInMem.saveAll(Arrays.asList(leftInventory, rightInventory));

        leftItem.setId(1);
        rightItem.setId(2);
        itemRepositoryInMemory.saveAll(Arrays.asList(leftItem, rightItem));
    }

    @Test
    void should_throw_ResistanceSocialNetworkException_when_no_such_left_item() {
        Exception e = assertThrows(ResistanceSocialNetworkException.class,
                () -> tradeRules.handle(leftRebel, rightRebel, 0, 0)
        );
        assertTrue(e.getMessage().contains("item not found with rebel id " + leftRebel.getId()));
    }

    @Test
    void should_throw_ResistanceSocialNetworkException_when_no_such_right_item() {
        Exception e = assertThrows(ResistanceSocialNetworkException.class,
                () -> tradeRules.handle(leftRebel, rightRebel, 1, 0)
        );
        assertTrue(e.getMessage().contains("item not found with rebel id " + rightRebel.getId()));
    }

    @Test
    void should_throw_TradeFailureException_when_leftRebel_traitor() {
        IntStream.range(0, 3).forEach(i -> leftRebel.setReportCounterUp());
        Exception e = assertThrows(ResistanceSocialNetworkException.class,
                () -> tradeRules.handle(leftRebel, rightRebel, 1, 2)
        );
        assertTrue(e.getMessage().contains("left rebel is a traitor"));
    }

    @Test
    void should_throw_TradeFailureException_when_rightRebel_traitor() {
        IntStream.range(0, 3).forEach(i -> rightRebel.setReportCounterUp());
        Exception e = assertThrows(ResistanceSocialNetworkException.class,
                () -> tradeRules.handle(leftRebel, rightRebel, 1, 2)
        );
        assertTrue(e.getMessage().contains("right rebel is a traitor"));
    }

    @Test
    void should_throw_TradeFailureException_when_points_do_not_match() {
        Exception e = assertThrows(ResistanceSocialNetworkException.class,
                () -> tradeRules.handle(leftRebel, rightRebel, 1, 2)
        );
        assertTrue(e.getMessage().contains("points do not match"));
    }
}