package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.InventoryRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.RebelRepositoryInMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TradeRulesTest {
    private final RebelRepositoryInMemory rebelRepoInMem = new RebelRepositoryInMemory();
    private final InventoryRepositoryInMemory inventoryRepoInMem = new InventoryRepositoryInMemory();
    private final Item doritos = new Item("doritos", 1);
    private final Item water = new Item("water", 2);
    private final Inventory lukeInv = new Inventory(List.of(doritos));
    private final Inventory leiaInv = new Inventory(List.of(water));
    private final TradeRules tradeRules = new TradeRules(inventoryRepoInMem);
    private Rebel luke;
    private Rebel leia;

    @BeforeEach
    void setUp() {
        luke = new Rebel("luke", 18, "male");
        leia = new Rebel("leia", 30, "female");

        luke.setId(1);
        rebelRepoInMem.saveInMem(luke);
        leia.setId(2);
        rebelRepoInMem.saveInMem(leia);

        lukeInv.setId(1);
        lukeInv.setRebel(luke);
        leiaInv.setId(2);
        leiaInv.setRebel(leia);

        inventoryRepoInMem.saveInMem(lukeInv);
        inventoryRepoInMem.saveInMem(leiaInv);
    }

    @Test
    void should_throw_TradeFailureException_when_source_inventory_not_found() {
        Exception e = assertThrows(TradeFailureException.class,
                () -> tradeRules.check(12367, doritos.getName(), leiaInv.getId(), water.getName())
        );
        assertTrue(e.getMessage().contains("source inventory not found"));
    }

    @Test
    void should_throw_TradeFailureException_when_target_inventory_not_found() {
        Exception e = assertThrows(TradeFailureException.class,
                () -> tradeRules.check(lukeInv.getId(), doritos.getName(), 3409587, water.getName())
        );
        assertTrue(e.getMessage().contains("target inventory not found"));
    }

    @Test
    void should_throw_NoSuchElementException_when_no_such_item_source() {
        Exception e = assertThrows(TradeFailureException.class,
                () -> tradeRules.check(lukeInv.getId(), new Item().getName(), leiaInv.getId(), water.getName())
        );
        assertTrue(e.getMessage().contains("source item not found"));
    }

    @Test
    void should_throw_NoSuchElementException_when_no_such_item_target() {
        Exception e = assertThrows(TradeFailureException.class,
                () -> tradeRules.check(lukeInv.getId(), doritos.getName(), leiaInv.getId(), new Item().getName())
        );
        assertTrue(e.getMessage().contains("target item not found"));
    }

    @Test
    void should_throw_TradeFailureException_when_source_traitor() {
        IntStream.range(0, 3).forEach(i -> luke.setReportCounterUp());
        Exception e = assertThrows(TradeFailureException.class,
                () -> tradeRules.check(lukeInv.getId(), doritos.getName(), leiaInv.getId(), water.getName())
        );
        assertTrue(e.getMessage().contains("source rebel is a traitor"));
    }

    @Test
    void should_throw_TradeFailureException_when_target_traitor() {
        IntStream.range(0, 3).forEach(i -> leia.setReportCounterUp());
        Exception e = assertThrows(TradeFailureException.class,
                () -> tradeRules.check(lukeInv.getId(), doritos.getName(), leiaInv.getId(), water.getName())
        );
        assertTrue(e.getMessage().contains("target rebel is a traitor"));
    }

    @Test
    void should_throw_TradeFailureException_when_points_do_not_match() {
        Exception e = assertThrows(TradeFailureException.class,
                () -> tradeRules.check(lukeInv.getId(), doritos.getName(), leiaInv.getId(), water.getName())
        );
        assertTrue(e.getMessage().contains("points do not match"));
    }
}