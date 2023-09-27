package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.InventoryRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.ItemRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.RebelRepositoryInMemory;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
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
        luke.setId(1);
        leia.setId(2);
        rebelRepoInMem.saveAll(Arrays.asList(luke, leia));

        lukeInv.setId(1);
        leiaInv.setId(2);
        luke.setInventory(lukeInv);
        leia.setInventory(leiaInv);

        inventoryRepoInMem.saveAll(Arrays.asList(lukeInv, leiaInv));

        doritos.setId(1);
        water.setId(2);
        itemRepositoryInMemory.saveAll(Arrays.asList(doritos, water));
    }

    @Test
    void should_throw_ResistanceSocialNetworkException_when_no_such_item_source() {
        Exception e = assertThrows(ResistanceSocialNetworkException.class,
                () -> tradeRules.handle(luke, leia, 0, 0)
        );
        assertTrue(e.getMessage().contains("item not found with rebel id " + luke.getId()));
    }

    @Test
    void should_throw_NoSuchElementException_when_no_such_item_target() {
        Exception e = assertThrows(ResistanceSocialNetworkException.class,
                () -> tradeRules.handle(luke, leia, 1, 0)
        );
        System.out.println(e.getMessage());
        assertTrue(e.getMessage().contains("item not found with rebel id " + leia.getId()));
    }

    @Test
    void should_throw_TradeFailureException_when_source_traitor() {
        IntStream.range(0, 3).forEach(i -> luke.setReportCounterUp());
        Exception e = assertThrows(ResistanceSocialNetworkException.class,
                () -> tradeRules.handle(luke, leia, 1, 2)
        );
        assertTrue(e.getMessage().contains("left rebel is a traitor"));
    }

    @Test
    void should_throw_TradeFailureException_when_target_traitor() {
        IntStream.range(0, 3).forEach(i -> leia.setReportCounterUp());
        Exception e = assertThrows(ResistanceSocialNetworkException.class,
                () -> tradeRules.handle(luke, leia, 1, 2)
        );
        assertTrue(e.getMessage().contains("right rebel is a traitor"));
    }

    @Test
    void should_throw_TradeFailureException_when_points_do_not_match() {
        Exception e = assertThrows(ResistanceSocialNetworkException.class,
                () -> tradeRules.handle(luke, leia, 1, 2)
        );
        assertTrue(e.getMessage().contains("points do not match"));
    }
}