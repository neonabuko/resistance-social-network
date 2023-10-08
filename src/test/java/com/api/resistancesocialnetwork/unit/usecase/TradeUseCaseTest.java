package com.api.resistancesocialnetwork.unit.usecase;

import com.api.resistancesocialnetwork.entity.Inventory;
import com.api.resistancesocialnetwork.entity.Item;
import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.InventoryRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.ItemRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.RebelRepositoryInMemory;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.InventoryRepository;
import com.api.resistancesocialnetwork.request.facade.TradeFacade;
import com.api.resistancesocialnetwork.rules.TradeRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import com.api.resistancesocialnetwork.usecase.TradeUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TradeUseCaseTest {
    private final RebelRepositoryInMemory rebelRepoInMem = new RebelRepositoryInMemory();
    private final InventoryRepository inventoryRepoInMem = new InventoryRepositoryInMemory();
    private final ItemRepositoryInMemory itemRepoInMem = new ItemRepositoryInMemory();

    private final TradeRules tradeRules = new TradeRules();
    private final TradeUseCase tradeUseCase = new TradeUseCase(tradeRules, rebelRepoInMem, inventoryRepoInMem);

    private Inventory leftInventory;
    private Inventory rightInventory;
    private Item leftItem;
    private Item rightItem;

    private TradeFacade tradeFacade;

    @BeforeEach
    void setUp() {
        Rebel leftRebel = new Rebel("luke", 18, "male");
        Rebel rightRebel = new Rebel("leia", 30, "female");
        leftRebel.setId(1);
        rightRebel.setId(2);

        leftItem = new Item("doritos", 1);
        rightItem = new Item("fandango", 1);
        leftItem.setId(1);
        rightItem.setId(2);

        leftInventory = new Inventory(new ArrayList<>(Arrays.asList(leftItem)));
        rightInventory = new Inventory(new ArrayList<>(Arrays.asList(rightItem)));
        leftInventory.setId(1);
        rightInventory.setId(2);
        leftRebel.setInventory(leftInventory);
        rightRebel.setInventory(rightInventory);

        rebelRepoInMem.saveAll(Arrays.asList(leftRebel, rightRebel));
        itemRepoInMem.saveAll(Arrays.asList(leftItem, rightItem));
        inventoryRepoInMem.saveAll(Arrays.asList(leftInventory, rightInventory));
    }

    @Test
    void left_inventory_should_contain_right_item_after_trade() throws ResistanceSocialNetworkException {
        tradeFacade = new TradeFacade(1, 1, 2, 2);
        tradeUseCase.handle(tradeFacade);
        assertTrue(leftInventory.findItemBy(rightItem.getId()).isPresent());
    }

    @Test
    void right_inventory_should_contain_left_item_after_trade() throws ResistanceSocialNetworkException {
        tradeFacade = new TradeFacade(1, 1, 2, 2);
        tradeUseCase.handle(tradeFacade);
        assertTrue(rightInventory.getItems().contains(leftItem));
    }

    @Test
    void inventories_should_remain_the_same_if_trade_fails() {
        tradeFacade = new TradeFacade(null, 1, 2, 2);
        try {
            tradeUseCase.handle(tradeFacade);
        } catch (ResistanceSocialNetworkException ignored) {}

        Inventory expectedLeftInventory = new Inventory(Arrays.asList(leftItem));
        Inventory expectedRightInventory = new Inventory(Arrays.asList(rightItem));
        expectedLeftInventory.setId(leftInventory.getId());
        expectedRightInventory.setId(rightInventory.getId());

        assertEquals(leftInventory.toString(), expectedLeftInventory.toString());
        assertEquals(rightInventory.toString(), expectedRightInventory.toString());
    }

    @Test
    void should_throw_exception_when_trade_parameters_not_provided() {
        tradeFacade = null;
        Exception e = assertThrows(ResistanceSocialNetworkException.class, () ->
                tradeUseCase.handle(tradeFacade)
                );
        assertTrue(e.getMessage().contains("must provide trade parameters"));
    }


    @Test
    void should_throw_exception_when_right_rebel_not_found(){
        tradeFacade = new TradeFacade(1, 1, 20, 2);
        Exception e = assertThrows(ResistanceSocialNetworkException.class, () ->
                tradeUseCase.handle(tradeFacade));
        assertTrue(e.getMessage().contains("right rebel not found"));
    }
}