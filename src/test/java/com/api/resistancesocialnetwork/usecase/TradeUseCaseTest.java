package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.RebelRepository;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.InventoryRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.RebelRepositoryInMemory;
import com.api.resistancesocialnetwork.request.DTO.TradeDTO;
import com.api.resistancesocialnetwork.rules.TradeFailureException;
import com.api.resistancesocialnetwork.rules.TradeRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TradeUseCaseTest {
    private final InventoryRepositoryInMemory inventoryRepoInMem = new InventoryRepositoryInMemory();
    private final TradeRules tradeRules = new TradeRules();

    private final RebelRepositoryInMemory rebelRepoInMem = new RebelRepositoryInMemory();
    private final TradeUseCase tradeUseCase = new TradeUseCase(tradeRules, rebelRepoInMem);
    private Item doritos;
    private Item fandango;
    private Inventory lukeInv;
    private Inventory leiaInv;
    private TradeDTO tradeDTO;

    @BeforeEach
    void setUp() {
        Rebel luke = new Rebel("luke", 18, "male");
        Rebel leia = new Rebel("leia", 30, "female");

        doritos = new Item("doritos", 1);
        fandango = new Item("fandango", 1);

        lukeInv = new Inventory(
                new ArrayList<>(Arrays.asList(doritos))
        );
        leiaInv = new Inventory(
                new ArrayList<>(Arrays.asList(fandango))
        );

        lukeInv.setId(1);
        lukeInv.setRebel(luke);
        leiaInv.setId(2);
        leiaInv.setRebel(leia);

        doritos.setId(1);
        fandango.setId(2);

        inventoryRepoInMem.save(lukeInv);
        inventoryRepoInMem.save(leiaInv);
    }

    @Test
    void source_should_contain_fandango_after_trade() throws TradeFailureException {
        tradeDTO = new TradeDTO(1, 1, 2, 2);
        tradeUseCase.handle(tradeDTO);
        assertTrue(inventoryRepoInMem.findItemByName(lukeInv.getId(), fandango.getName()).isPresent());
    }

    @Test
    void target_should_contain_doritos_after_trade() throws TradeFailureException {
        tradeDTO = new TradeDTO(1, 1, 2, 2);
        tradeUseCase.handle(tradeDTO);
        assertTrue(leiaInv.getItems().contains(doritos));
    }

    @Test
    void inventories_should_remain_the_same_if_trade_fails() {
        tradeDTO = new TradeDTO(null, 1, 2, 2);
        try {
            tradeUseCase.handle(tradeDTO);
        } catch (TradeFailureException ignored) {}

        Inventory expectedLukeInventory = new Inventory(Arrays.asList(doritos));
        Inventory expectedLeiaInventory = new Inventory(Arrays.asList(fandango));
        expectedLukeInventory.setId(1);
        expectedLeiaInventory.setId(2);

        assertEquals(lukeInv.toString(), expectedLukeInventory.toString());
        assertEquals(leiaInv.toString(), expectedLeiaInventory.toString());
    }
}