package com.api.resistancesocialnetwork.integration.controller;

import com.api.resistancesocialnetwork.entity.Inventory;
import com.api.resistancesocialnetwork.entity.Item;
import com.api.resistancesocialnetwork.entity.Location;
import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.InventoryRepository;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.LocationRepository;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.RebelRepository;
import com.api.resistancesocialnetwork.usecase.statistics.AlliesTraitorsPercentagesUseCase;
import com.api.resistancesocialnetwork.usecase.statistics.ItemAveragesPerRebelUseCase;
import com.api.resistancesocialnetwork.usecase.statistics.ShowAlliesUseCase;
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
class AllianceStatsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShowAlliesUseCase showAlliesUseCase;

    @Autowired
    private AlliesTraitorsPercentagesUseCase alliesTraitorsPercentagesUseCase;

    @Autowired
    private ItemAveragesPerRebelUseCase itemAveragesPerRebelUseCase;

    @Autowired
    private RebelRepository rebelRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private LocationRepository locationRepository;

    Rebel rebelLeft = new Rebel("jooj", 18, "male");
    Rebel rebelRight = new Rebel("jeej", 81, "maam");
    Location location1 = new Location(22.2, 22.2, "base");
    Location location2 = new Location(22.2, 22.2, "base");
    Item food = new Item("food", 1);
    Item water = new Item("water", 1);
    Inventory inventoryLeft = new Inventory(new ArrayList<>(Arrays.asList(food)));
    Inventory inventoryRight = new Inventory(new ArrayList<>(Arrays.asList(water)));

    private String token;

    @BeforeEach
    void setUp() throws Exception {
        rebelLeft.setInventory(inventoryLeft);
        rebelRight.setInventory(inventoryRight);
        rebelLeft.setLocation(location1);
        rebelRight.setLocation(location2);
        rebelRepository.saveAll(Arrays.asList(rebelLeft, rebelRight));
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

    @Test
    void should_return_200_when_hit_show_allies() throws Exception{
        mockMvc.perform(get("/stats/show-allies")
                        .header("Authorization", "Bearer " + token)
                ).andExpect(status().isOk());
    }

    @Test
    void should_return_200_when_hit_allies_traitors_percentages() throws Exception{
        mockMvc.perform(get("/stats/show-allies-traitors-percentages")
                        .header("Authorization", "Bearer " + token)
                ).andExpect(status().isOk());
    }

    @Test
    void should_return_200_when_hit_average_number_items() throws Exception{
        mockMvc.perform(get("/stats/show-average-number-items")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }



    @Test
    void should_return_405_when_POST_average_number_items() throws Exception {
        String requestBody = "";
        mockMvc.perform(post("/stats/show-average-number-items")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().is(405));
    }

    @Test
    void should_return_405_when_PATCH_average_number_items() throws Exception {
        String requestBody = "";
        mockMvc.perform(patch("/stats/show-average-number-items")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().is(405));
    }

    @Test
    void should_return_405_when_POST_show_allies_percentages() throws Exception {
        String requestBody = "";
        mockMvc.perform(post("/stats/show-allies-traitors-percentages")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().is(405));
    }

    @Test
    void should_return_405_when_patch_show_allies_percentages() throws Exception {
        String requestBody = "";
        mockMvc.perform(patch("/stats/show-allies-traitors-percentages")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().is(405));
    }

    @Test
    void should_return_405_when_POST_show_allies() throws Exception {
        String requestBody = "";
        mockMvc.perform(post("/stats/show-allies")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().is(405));
    }

    @Test
    void should_return_405_when_PATCH_show_allies() throws Exception {
        String requestBody = "";
        mockMvc.perform(patch("/stats/show-allies")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().is(405));
    }


}