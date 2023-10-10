package com.api.resistancesocialnetwork.integration.controller;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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

    Rebel rebelLeft = new Rebel("jooj", 18, "male");
    Rebel rebelRight = new Rebel("jeej", 81, "maam");
    Location locationLeft = new Location(22.1, 22.1, "base");
    Item food = new Item("food", 1);
    Item water = new Item("water", 1);
    Inventory inventoryLeft = new Inventory(new ArrayList<>(Arrays.asList(food)));
    Inventory inventoryRight = new Inventory(new ArrayList<>(Arrays.asList(water)));

    private String token;

    @BeforeEach
    void setUp() throws Exception {
        login();
    }

    void login() throws Exception {
        String requestBody = "{\"username\":\"JuuJ\",\"password\":\"soos\",\"role\":\"ADMIN\"}";

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        );

        String loginBody = "{\"username\":\"JuuJ\",\"password\":\"soos\"}";

        MvcResult mvcResult = mockMvc.perform(post("/auth/username")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginBody)).andReturn();
        token = mvcResult.getResponse().getContentAsString();
    }

    /* ------------------------------- 200 OK ---------------------------------*/

//    REPORT:
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
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
    }

//    UPDATE LOCATION:
    @Test
    @Transactional
    void should_return_200_when_location_update_ok() throws Exception {
        rebelRepository.save(rebelLeft);
        locationRepository.save(locationLeft);
        rebelLeft.setLocation(locationLeft);

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
                        .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
    }

//    TRADE:
    @Test
    @Transactional
    void should_return_200_when_trade_ok() throws Exception {
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
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
    }

    /* ------------------------------- 405 METHOD NOT ALLOWED ---------------------------------*/

//    REPORT:
    @Test
    void should_return_405_when_POST_report() throws Exception {
        String requestBody = "";
        mockMvc.perform(post("/rebel/report")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().is(405));
    }

    @Test
    void should_return_405_when_GET_report() throws Exception {
        String requestBody = "";
        mockMvc.perform(get("/rebel/report")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().is(405));
    }


//    UPDATE LOCATION:
    @Test
    void should_return_405_when_POST_location_update() throws Exception {
        String requestBody = "";
        mockMvc.perform(post("/rebel/update-location")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().is(405));
    }

    @Test
    void should_return_405_when_GET_location_update() throws Exception {
        mockMvc.perform(get("/rebel/update-location")
                        .header("Authorization", "Bearer " + token)
                ).andExpect(status().is(405));
    }


//    TRADE:
    @Test
    void should_return_405_when_POST_trade() throws Exception {
        String requestBody = "";
        mockMvc.perform(post("/rebel/trade")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().is(405));
    }

    @Test
    void should_return_405_when_GET_trade() throws Exception {
        mockMvc.perform(get("/rebel/trade")
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().is(405));
    }


    /* ------------------------------- 400 BAD REQUEST ---------------------------------*/

//    REPORT:
    @Test
    void should_return_400_when_invalid_report() throws Exception {
        mockMvc.perform(patch("/rebel/report")
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().is(400));
    }

//    UPDATE LOCATION:
    @Test
    void should_return_400_when_invalid_location_update() throws Exception {
        mockMvc.perform(patch("/rebel/update-location")
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().is(400));
    }

//    TRADE:
    @Test
    void should_return_400_when_invalid_trade() throws Exception {
        mockMvc.perform(patch("/rebel/trade")
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().is(400));
    }
}