package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.InventoryRepository;
import com.api.resistancesocialnetwork.repositories.ItemRepository;
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
    private RebelRepository rebelRepo;
    @Autowired
    private InventoryRepository inventoryRepo;
    @Autowired
    private ItemRepository itemRepo;
    private final Rebel luke = new Rebel("luke", 18, "male");
    private final Rebel leia = new Rebel("leia", 30, "female");
    private final Item doritos = new Item("doritos", 1);
    private final Item water = new Item("water", 2);
    private final Inventory lukeInv = new Inventory(new ArrayList<>( List.of( doritos ) ));
    private final Inventory leiaInv = new Inventory(new ArrayList<>( List.of( water ) ));
    @Autowired
    private TradeRules tradeRules;

    @BeforeEach
    void setUp() {
        rebelRepo.save(luke);
        rebelRepo.save(leia);

        lukeInv.setRebel(luke);
        leiaInv.setRebel(leia);

        doritos.setInventory(lukeInv);
        water.setInventory(leiaInv);

        inventoryRepo.save(lukeInv);
        inventoryRepo.save(leiaInv);

        itemRepo.save(doritos);
        itemRepo.save(water);
    }

    @Test
    void should_throw_TradeFailureException_when_source_inventory_not_found() {
        Exception e = assertThrows(TradeFailureException.class, () ->
                tradeRules.check(0, doritos, 2, water)
        );
        System.out.println(e.getMessage());
        assertTrue(e.getMessage().contains("source inventory not found"));
    }

    @Test
    void should_throw_TradeFailureException_when_target_inventory_not_found() {
        inventoryRepo.save(lukeInv);
        Exception e = assertThrows(TradeFailureException.class, () ->
                tradeRules.check(lukeInv.getId(), doritos, 0, water)
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
        rebelRepo.save(luke);
        rebelRepo.save(leia);
        inventoryRepo.save(lukeInv);
        inventoryRepo.save(leiaInv);

        Exception e = assertThrows(TradeFailureException.class, () ->
                tradeRules.check(1, doritos, 2, water)
        );
        assertTrue(e.getMessage().contains("source rebel is a traitor"));
    }

    @Test
    void should_throw_TradeFailureException_when_target_traitor() {
        leia.setReportCounterUp();
        leia.setReportCounterUp();
        leia.setReportCounterUp();
        rebelRepo.save(leia);
        Exception e = assertThrows(TradeFailureException.class, () ->
                tradeRules.check(lukeInv.getId(), doritos, leiaInv.getId(), water)
        );
        assertTrue(e.getMessage().contains("target rebel is a traitor"));
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