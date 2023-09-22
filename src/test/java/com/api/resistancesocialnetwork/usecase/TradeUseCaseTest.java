package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.InventoryRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.ItemRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.RebelRepositoryInMemory;
import com.api.resistancesocialnetwork.rules.TradeFailureException;
import com.api.resistancesocialnetwork.rules.TradeRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TradeUseCaseTest {
    private final RebelRepositoryInMemory rebelRepoInMem = new RebelRepositoryInMemory();
    private final InventoryRepositoryInMemory inventoryRepoInMem = new InventoryRepositoryInMemory();
    private final ItemRepositoryInMemory itemRepoInMem = new ItemRepositoryInMemory();
    private final TradeRules tradeRules = new TradeRules(inventoryRepoInMem, rebelRepoInMem);
    private final TradeUseCase tradeUseCase = new TradeUseCase(inventoryRepoInMem, tradeRules, itemRepoInMem);
    private Item doritos;
    private Item fandango;
    private Inventory lukeInv;
    private Inventory leiaInv;
    private Rebel luke;
    private Rebel leia;

    @BeforeEach
    void setUp() {
        luke = new Rebel("luke", 18, "male");
        leia = new Rebel("leia", 30, "female");
        doritos = new Item("doritos", 1);
        fandango = new Item("fandango", 1);
        lukeInv = new Inventory(Arrays.asList(doritos));
        leiaInv = new Inventory(Arrays.asList(fandango));

        luke.setId(1);
        rebelRepoInMem.saveInMem(luke);
        leia.setId(2);
        rebelRepoInMem.saveInMem(leia);

        lukeInv.setId(1);
        lukeInv.setRebel(luke);
        leiaInv.setId(2);
        leiaInv.setRebel(leia);

        doritos.setId(1);
        doritos.setInventory(lukeInv);
        fandango.setId(2);
        fandango.setInventory(leiaInv);

        inventoryRepoInMem.saveInMem(lukeInv);
        inventoryRepoInMem.saveInMem(leiaInv);

        itemRepoInMem.saveInMem(doritos);
        itemRepoInMem.saveInMem(fandango);
    }

    @Test
    void source_should_contain_fandango_after_trade() throws TradeFailureException {
        tradeUseCase.handle(lukeInv.getId(), doritos, leiaInv.getId(), fandango);
        assertTrue(inventoryRepoInMem.findItemByName(lukeInv.getId(), fandango.getName()).isPresent());
    }

    @Test
    void target_should_contain_doritos() throws TradeFailureException {
        tradeUseCase.handle(lukeInv.getId(), doritos, leiaInv.getId(), fandango);
        assertTrue(leiaInv.getItems().contains(doritos));
    }
}