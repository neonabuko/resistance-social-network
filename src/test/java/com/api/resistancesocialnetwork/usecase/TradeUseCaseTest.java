package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.InventoryRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.ItemRepositoryInMemory;
import com.api.resistancesocialnetwork.rules.TradeFailureException;
import com.api.resistancesocialnetwork.rules.TradeRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TradeUseCaseTest {
    private final InventoryRepositoryInMemory inventoryRepoInMem = new InventoryRepositoryInMemory();
    private final ItemRepositoryInMemory itemRepoInMem = new ItemRepositoryInMemory();
    private final TradeRules tradeRules = new TradeRules(inventoryRepoInMem);
    private final TradeUseCase tradeUseCase = new TradeUseCase(inventoryRepoInMem, tradeRules, itemRepoInMem);
    private Item doritos;
    private Item fandango;
    private Inventory lukeInv;
    private Inventory leiaInv;

    @BeforeEach
    void setUp() {
        Rebel luke = new Rebel("luke", 18, "male");
        Rebel leia = new Rebel("leia", 30, "female");
        doritos = new Item("doritos", 1);
        fandango = new Item("fandango", 1);
        lukeInv = new Inventory(Arrays.asList(doritos));
        leiaInv = new Inventory(Arrays.asList(fandango));

        lukeInv.setId(1);
        lukeInv.setRebel(luke);
        leiaInv.setId(2);
        leiaInv.setRebel(leia);

        inventoryRepoInMem.saveInMem(lukeInv);
        inventoryRepoInMem.saveInMem(leiaInv);
    }

    @Test
    void source_should_contain_fandango_after_trade() throws TradeFailureException {
        tradeUseCase.handle(lukeInv.getId(), doritos.getName(), leiaInv.getId(), fandango.getName());
        assertTrue(inventoryRepoInMem.findItemByName(lukeInv.getId(), fandango.getName()).isPresent());
    }

    @Test
    void target_should_contain_doritos_after_trade() throws TradeFailureException {
        tradeUseCase.handle(lukeInv.getId(), doritos.getName(), leiaInv.getId(), fandango.getName());
        assertTrue(leiaInv.getItems().contains(doritos));
    }

    @Test
    void should_not_trade_when_something_unexpected_happens() {
        lukeInv.setId(null);
        try {
            tradeUseCase.handle(lukeInv.getId(), doritos.getName(), leiaInv.getId(), fandango.getName());
        } catch (TradeFailureException ignored) {
        }
        assertEquals(lukeInv.getItems().toString(), Arrays.asList(new Item("doritos", 1)).toString());
    }
}