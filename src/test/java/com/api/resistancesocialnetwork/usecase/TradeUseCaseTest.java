package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.InventoryRepository;
import com.api.resistancesocialnetwork.repositories.RebelRepository;
import com.api.resistancesocialnetwork.rules.TradeFailureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class TradeUseCaseTest {
    @Autowired
    private RebelRepository rebelRepository;
    @Autowired
    private InventoryRepository inventoryRepo;
    @Autowired
    private TradeUseCase tradeUseCase;
    private final Rebel luke = new Rebel("luke", 18, "male");
    private final Rebel leia = new Rebel("leia", 30, "female");
    private final Item doritos = new Item("doritos", 1);
    private final Item fandango = new Item("fandango", 1);
    private final Inventory lukeInv = new Inventory(new ArrayList<>( Arrays.asList(doritos) ));
    private final Inventory leiaInv = new Inventory(new ArrayList<>( Arrays.asList(fandango) ));

    @BeforeEach
    void setUp() {
        rebelRepository.save(luke);
        rebelRepository.save(leia);
        inventoryRepo.save(lukeInv);
        inventoryRepo.save(leiaInv);
    }

    @Test
    void should_add_to_list() throws TradeFailureException {

        tradeUseCase.handle(lukeInv.getId(), doritos, leiaInv.getId(), fandango);

        assertTrue(inventoryRepo.findById(lukeInv.getId())
                .get()
                .getItems()
                .stream()
                .anyMatch(item -> item.getName().equals(fandango.getName())
                ));
    }
}