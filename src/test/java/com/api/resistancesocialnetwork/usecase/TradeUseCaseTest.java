package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.InventoryRepository;
import com.api.resistancesocialnetwork.repositories.ItemRepository;
import com.api.resistancesocialnetwork.repositories.RebelRepository;
import com.api.resistancesocialnetwork.rules.TradeFailureException;
import com.api.resistancesocialnetwork.rules.TradeRules;
import jakarta.annotation.security.RunAs;
import org.apache.catalina.core.ApplicationContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


class TradeUseCaseTest {
    private TradeUseCase tradeUseCase;



    private Rebel luke;
    private Rebel leia;
    private Item doritos;
    private Item fandango;
    private Inventory lukeInv;
    private Inventory leiaInv;

    @BeforeEach
    void setUp() {
        luke = new Rebel("luke", 18, "male");
        leia = new Rebel("leia", 30, "female");
        doritos = new Item("doritos", 1);
        fandango = new Item("fandango", 1);
        lukeInv = new Inventory(List.of(doritos));
        leiaInv = new Inventory(List.of(fandango));
    }

    @Test
    void source_should_contain_doritos() {

//        tradeUseCase = new TradeUseCase()
//        Assertions.assertTrue(inventoryRepoInMem.getItems().contains(doritos));
    }

    @Test
    void source_should_contain_fandango_after_trade() throws TradeFailureException{
        tradeUseCase.handle(lukeInv.getId(), doritos, leiaInv.getId(), fandango);
        Assertions.assertTrue(lukeInv.findItemByName(fandango.getName()).isPresent());
    }

    @Test
    void should_add_to_list() throws TradeFailureException {
        tradeUseCase.handle(lukeInv.getId(), doritos, leiaInv.getId(), fandango);
        Assertions.assertTrue(leiaInv.getItems().contains(doritos));
    }
}