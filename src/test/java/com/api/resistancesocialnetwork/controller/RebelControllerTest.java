package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.entity.Inventory;
import com.api.resistancesocialnetwork.entity.Item;
import com.api.resistancesocialnetwork.entity.Location;
import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.InventoryRepository;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.ItemRepository;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.LocationRepository;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.RebelRepository;
import com.api.resistancesocialnetwork.usecase.LocationUpdateUseCase;
import com.api.resistancesocialnetwork.usecase.ReportUseCase;
import com.api.resistancesocialnetwork.usecase.TradeUseCase;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class RebelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReportUseCase reportUseCase;

    @Autowired
    private LocationUpdateUseCase locationUpdateUseCase;

    @Autowired
    private TradeUseCase tradeUseCase;

    @Autowired
    private RebelRepository rebelRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void should_return_200_when_report_ok() throws Exception {
        rebelRepository.save(new Rebel());
        rebelRepository.save(new Rebel());

        String requestBody = "{" +
                                "\"report\": {" +
                                    "\"reportingId\":1," +
                                    "\"reportedId\":2" +
                                "}" +
                              "}";

        mockMvc.perform(patch("/rebel/report")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isOk());
    }

    @Test
    void should_return_200_when_location_update_ok() throws Exception {
        locationRepository.save(new Location(22.2, 22.2, "base"));
        String requestBody = "{" +
                                "\"locationUpdate\": {" +
                                    "\"location\": {" +
                                        "\"id\":1," +
                                        "\"latitude\":109.23," +
                                        "\"longitude\":-2346," +
                                        "\"base\":\"new-base\"" +
                                    "}" +
                                "}" +
                             "}";
        mockMvc.perform(
                patch("/rebel/update-location")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isOk());
    }

    @Test
    @Transactional
    void should_return_200_when_trade_ok() throws Exception {
        Rebel rebelLeft = new Rebel("jooj", 18, "male");
        Rebel rebelRight = new Rebel("jeej", 81, "maam");
        Item food = new Item("food", 1);
        Item water = new Item("water", 1);
        Inventory inventoryLeft = new Inventory(new ArrayList<>(Arrays.asList(food)));
        Inventory inventoryRight = new Inventory(new ArrayList<>(Arrays.asList(water)));

        rebelRepository.saveAll(Arrays.asList(rebelLeft, rebelRight));
        rebelLeft.setInventory(inventoryLeft);
        rebelRight.setInventory(inventoryRight);
        inventoryRepository.save(inventoryLeft);
        inventoryRepository.save(inventoryRight);

        itemRepository.saveAll(Arrays.asList(food, water));
        String requestBody = "{" +
                                "\"trade\": {" +
                                    "\"leftRebelId\":1," +
                                    "\"leftItemId\":1," +
                                    "\"rightRebelId\":2," +
                                    "\"rightItemId\":2" +
                                "}" +
                             "}";

        mockMvc.perform(patch("/rebel/trade")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isOk());
    }
}