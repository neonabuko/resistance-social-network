package com.api.resistancesocialnetwork.repositories;

import com.api.resistancesocialnetwork.model.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class InventoryRepositoryTest {
    @Autowired
    private InventoryRepository inventoryRepo;
    @Test
    void findItemByName_should_return_Optional_Empty_when_item_not_found() {
        Optional<Item> actualItem = inventoryRepo.findItemByName(0, "alberto");
        Optional<Item> expectedItem = Optional.empty();
        assertEquals(expectedItem, actualItem);
    }
}