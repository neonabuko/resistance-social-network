package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.InventoryRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.ItemRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.RebelRepositoryInMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TradeRulesTest {

    private final RebelRepositoryInMemory rebelRepoInMem = new RebelRepositoryInMemory();
    private final InventoryRepositoryInMemory inventoryRepoInMem = new InventoryRepositoryInMemory();
    private final ItemRepositoryInMemory itemRepo = new ItemRepositoryInMemory();

    private final Rebel luke = new Rebel("luke", 18, "male");
    private final Rebel leia = new Rebel("leia", 30, "female");
    private final Item doritos = new Item("doritos", 1);
    private final Item water = new Item("water", 2);
    private final Inventory lukeInv = new Inventory(new ArrayList<>( List.of( doritos ) ));
    private final Inventory leiaInv = new Inventory(new ArrayList<>( List.of( water ) ));

    private final TradeRules tradeRules = new TradeRules(inventoryRepoInMem, rebelRepoInMem);

    @BeforeEach
    void setUp() {
        luke.setId(1);
        rebelRepoInMem.save(luke);
        leia.setId(2);
        rebelRepoInMem.save(leia);

        lukeInv.setId(1);
        lukeInv.setRebel(luke);
        leiaInv.setId(2);
        leiaInv.setRebel(leia);

        doritos.setId(1);
        doritos.setInventory(lukeInv);
        water.setId(2);
        water.setInventory(leiaInv);

        inventoryRepoInMem.save(lukeInv);
        inventoryRepoInMem.save(leiaInv);

        itemRepo.save(doritos);
        itemRepo.save(water);
    }

    @Test
    void should_throw_TradeFailureException_when_source_inventory_not_found() {
        Exception e = assertThrows(TradeFailureException.class, () ->
                tradeRules.check(12367, doritos, leiaInv.getId(), water)
        );
        System.out.println(e.getMessage());
        assertTrue(e.getMessage().contains("source inventory not found"));
    }

    @Test
    void should_throw_TradeFailureException_when_target_inventory_not_found() {
        inventoryRepoInMem.save(lukeInv);
        Exception e = assertThrows(TradeFailureException.class, () ->
                tradeRules.check(lukeInv.getId(), doritos, 3409587, water)
        );
        assertTrue(e.getMessage().contains("target inventory not found"));
    }

    @Test
    void should_throw_NoSuchElementException_when_no_such_item_source() {
        Exception e = assertThrows(TradeFailureException.class, () ->
                tradeRules.check(lukeInv.getId(), new Item(), leiaInv.getId(), water)
        );
        assertTrue(e.getMessage().contains("no such item source"));
    }

    @Test
    void should_throw_NoSuchElementException_when_no_such_item_target() {
        Exception e = assertThrows(TradeFailureException.class, () ->
                tradeRules.check(lukeInv.getId(), doritos, leiaInv.getId(), new Item())
        );
        System.out.println(e.getMessage());
        assertTrue(e.getMessage().contains("no such item target"));
    }

    @Test
    void should_throw_TradeFailureException_when_source_traitor() {
        luke.setReportCounterUp();
        luke.setReportCounterUp();
        luke.setReportCounterUp();
        rebelRepoInMem.save(luke);
        rebelRepoInMem.save(leia);
        inventoryRepoInMem.save(lukeInv);
        inventoryRepoInMem.save(leiaInv);

        Exception e = assertThrows(TradeFailureException.class, () ->
                tradeRules.check(lukeInv.getId(), doritos, leiaInv.getId(), water)
        );

        assertTrue(e.getMessage().contains("source rebel is a traitor"));
    }

    @Test
    void should_throw_TradeFailureException_when_target_traitor() {
        leia.setReportCounterUp();
        leia.setReportCounterUp();
        leia.setReportCounterUp();
        rebelRepoInMem.save(leia);
        Exception e = assertThrows(TradeFailureException.class, () ->
                tradeRules.check(lukeInv.getId(), doritos, leiaInv.getId(), water)
        );
        assertTrue(e.getMessage().contains("target rebel is a traitor"));
    }

    @Test
    void should_throw_TradeFailureException_when_points_do_not_match() {
        Exception e = assertThrows(TradeFailureException.class, () ->
                tradeRules.check(lukeInv.getId(), doritos,
                        leiaInv.getId(), water)
        );
        assertTrue(e.getMessage().contains("points do not match"));
    }
}