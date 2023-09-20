package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.InventoryRepository;
import com.api.resistancesocialnetwork.repositories.RebelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TradeRulesTest {
    @Autowired
    private RebelRepository rebelRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    private final Rebel luke = new Rebel("luke", 18, "male");
    private final Rebel leia = new Rebel("leia", 30, "female");
    private final Inventory lukeInv = new Inventory(new ArrayList<>( List.of( new Item("doritos", 1)) ));
    private final Inventory leiaInv = new Inventory(new ArrayList<>( List.of( new Item("water", 2)) ));
    @Autowired
    private TradeRules tradeRules;

    @BeforeEach
    void setUp() {
        rebelRepository.save(luke);
        rebelRepository.save(leia);
        inventoryRepository.save(lukeInv);
        inventoryRepository.save(leiaInv);
    }

    @Test
    void should_throw_TradeFailureException_when_source_inventory_not_found() {
        inventoryRepository.deleteById(lukeInv.getId());
        Exception e = assertThrows(TradeFailureException.class, () ->
                tradeRules.check(lukeInv.getId(), new Item("food", 1),
                        leiaInv.getId(), new Item("water", 2))
        );
        assertTrue(e.getMessage().contains("source inventory not found"));
    }

    @Test
    void should_throw_TradeFailureException_when_target_inventory_not_found() {
        inventoryRepository.deleteById(leiaInv.getId());
        Exception e = assertThrows(TradeFailureException.class, () ->
                tradeRules.check(lukeInv.getId(), new Item("food", 1),
                        leiaInv.getId(), new Item("water", 2))
        );
        assertTrue(e.getMessage().contains("target inventory not found"));
    }

    @Test
    void should_throw_NoSuchElementException_when_no_such_item_source() {
        Exception e = assertThrows(TradeFailureException.class, () ->
                tradeRules.check(lukeInv.getId(), new Item("food", 1),
                        leiaInv.getId(), new Item("water", 2))
        );
        assertTrue(e.getMessage().contains("no such item source"));
    }

    @Test
    void should_throw_NoSuchElementException_when_no_such_item_target() {
        Exception e = assertThrows(TradeFailureException.class, () ->
                tradeRules.check(lukeInv.getId(), new Item("doritos", 1),
                        leiaInv.getId(), new Item("cheetos", 2))
        );
        assertTrue(e.getMessage().contains("no such item target"));
    }

    @Test
    void should_throw_TradeFailureException_when_source_traitor() {
        luke.setReportCounterUp();
        luke.setReportCounterUp();
        luke.setReportCounterUp();
        rebelRepository.save(luke);
        Exception e = assertThrows(TradeFailureException.class, () ->
                tradeRules.check(1, new Item("doritos", 1),
                        2, new Item("cheetos", 2))
        );
        assertTrue(e.getMessage().contains("source rebel is either a traitor or could not be found"));
    }

    @Test
    void should_throw_TradeFailureException_when_target_traitor() {
        leia.setReportCounterUp();
        leia.setReportCounterUp();
        leia.setReportCounterUp();
        rebelRepository.save(leia);
        Exception e = assertThrows(TradeFailureException.class, () ->
                tradeRules.check(lukeInv.getId(), new Item("doritos", 1),
                        leiaInv.getId(), new Item("cheetos", 2))
        );
        assertTrue(e.getMessage().contains("target rebel is either a traitor or could not be found"));
    }

    @Test
    void should_throw_TradeFailureException_when_points_do_not_match() {
        Exception e = assertThrows(TradeFailureException.class, () ->
                tradeRules.check(lukeInv.getId(), new Item("doritos", 2),
                        leiaInv.getId(), new Item("water", 2))
        );
        assertTrue(e.getMessage().contains("points do not match"));
    }
}