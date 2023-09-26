package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.InventoryRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.ItemRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.RebelRepositoryInMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class TradeRulesTest {
    private final RebelRepositoryInMemory rebelRepoInMem = new RebelRepositoryInMemory();
    private final InventoryRepositoryInMemory inventoryRepoInMem = new InventoryRepositoryInMemory();
    private final ItemRepositoryInMemory itemRepositoryInMemory = new ItemRepositoryInMemory();
    private final Item doritos = new Item("doritos", 1);
    private final Item water = new Item("water", 2);
    private final Inventory lukeInv = new Inventory(List.of(doritos));
    private final Inventory leiaInv = new Inventory(List.of(water));
    private final TradeRules tradeRules = new TradeRules();
    private Rebel luke;
    private Rebel leia;

    @BeforeEach
    void setUp() {
        luke = new Rebel("luke", 18, "male");
        leia = new Rebel("leia", 30, "female");

        rebelRepoInMem.save(luke);
        rebelRepoInMem.save(leia);

        luke.setInventory(lukeInv);
        leia.setInventory(leiaInv);

        inventoryRepoInMem.save(lukeInv);
        inventoryRepoInMem.save(leiaInv);

        itemRepositoryInMemory.save(doritos);
        itemRepositoryInMemory.save(water);
    }

    @Test
    void should_throw_NoSuchElementException_when_no_such_item_source() {
        Exception e = assertThrows(TradeFailureException.class,
                () -> tradeRules.handle(luke, leia, 0, 0)
        );
        assertTrue(e.getMessage().contains("Inventory id " + lukeInv.getId() + ": item not found"));
    }

    @Test
    void should_throw_NoSuchElementException_when_no_such_item_target() {
        Exception e = assertThrows(TradeFailureException.class,
                () -> tradeRules.handle(luke, leia, 1, 0)
        );
        assertTrue(e.getMessage().contains("Inventory id " + leiaInv.getId() + ": item not found"));
    }

    @Test
    void should_throw_TradeFailureException_when_source_traitor() {
        IntStream.range(0, 3).forEach(i -> luke.setReportCounterUp());
        Exception e = assertThrows(TradeFailureException.class,
                () -> tradeRules.handle(luke, leia, 1, 2)
        );
        assertTrue(e.getMessage().contains("source rebel is a traitor"));
    }

    @Test
    void should_throw_TradeFailureException_when_target_traitor() {
        IntStream.range(0, 3).forEach(i -> leia.setReportCounterUp());
        Exception e = assertThrows(TradeFailureException.class,
                () -> tradeRules.handle(luke, leia, 1, 2)
        );
        assertTrue(e.getMessage().contains("target rebel is a traitor"));
    }

    @Test
    void should_throw_TradeFailureException_when_points_do_not_match() {
        Exception e = assertThrows(TradeFailureException.class,
                () -> tradeRules.handle(luke, leia, 1, 2)
        );
        assertTrue(e.getMessage().contains("points do not match"));
    }
}