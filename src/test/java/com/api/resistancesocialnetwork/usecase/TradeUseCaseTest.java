package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.InventoryRepository;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.InventoryRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.ItemRepositoryInMemory;
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
    private final TradeRules tradeRules = new TradeRules();
    private final RebelRepositoryInMemory rebelRepoInMem = new RebelRepositoryInMemory();
    private final InventoryRepository inventoryRepoInMem = new InventoryRepositoryInMemory();
    private final TradeUseCase tradeUseCase = new TradeUseCase(tradeRules, rebelRepoInMem, inventoryRepoInMem);
    private Item doritos;
    private Item fandango;
    private Inventory lukeInv;
    private Inventory leiaInv;
    private TradeDTO tradeDTO;
    private final ItemRepositoryInMemory itemRepoInMem = new ItemRepositoryInMemory();

    @BeforeEach
    void setUp() {
        Rebel luke = new Rebel("luke", 18, "male");
        Rebel leia = new Rebel("leia", 30, "female");
        luke.setId(1);
        leia.setId(2);
        doritos = new Item("doritos", 1);
        fandango = new Item("fandango", 1);

        lukeInv = new Inventory(
                new ArrayList<>(Arrays.asList(doritos))
        );
        leiaInv = new Inventory(
                new ArrayList<>(Arrays.asList(fandango))
        );

        luke.setInventory(lukeInv);
        leia.setInventory(leiaInv);

        rebelRepoInMem.saveAll(Arrays.asList(luke, leia));
        itemRepoInMem.saveAll(Arrays.asList(doritos, fandango));
        inventoryRepoInMem.saveAll(Arrays.asList(lukeInv, leiaInv));
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