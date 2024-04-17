package com.api.resistancesocialnetwork.integration.controller;

import com.api.resistancesocialnetwork.entity.*;
import com.api.resistancesocialnetwork.enums.UserRole;
import com.api.resistancesocialnetwork.repository.repositoryinterfaces.*;
import com.api.resistancesocialnetwork.usecase.ReportUseCase;
import com.api.resistancesocialnetwork.usecase.TradeUseCase;
import com.api.resistancesocialnetwork.usecase.UpdateLocationUseCase;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RebelControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ReportUseCase report;
    @Autowired
    private UpdateLocationUseCase locationUpdate;
    @Autowired
    private TradeUseCase trade;
    @Autowired
    private RebelRepository rebelRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ResistanceUserRepository resistanceUserRepository;

    Rebel rebelLeft = new Rebel("jooj", 18, "male");
    Rebel rebelRight = new Rebel("jeej", 81, "maam");
    Location locationLeft = new Location(22.1, 22.1, "base");
    Item food = new Item("food", 1);
    Item water = new Item("water", 1);
    Inventory inventoryLeft = new Inventory(new ArrayList<>(Arrays.asList(food)));
    Inventory inventoryRight = new Inventory(new ArrayList<>(Arrays.asList(water)));

    private String signup_then_login(String username, String password, String role) throws Exception {
        String requestBody = "{" +
                                "\"username\":\"" + username + "\"," +
                                "\"password\":\"" + password + "\"," +
                                "\"role\":\"" + role + "\"" +
                             "}";

        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        );

        String loginBody = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";

        MvcResult mvcResult = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginBody)).andReturn();
        return mvcResult.getResponse().getContentAsString();
    }

    private void create_profile(String requestBody, String token) throws Exception {
        mockMvc.perform(post("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .header("Authorization", "Bearer " + token)
        ).andReturn();
    }

    /* ------------------------------- 200 OK ---------------------------------*/

//    REPORT:
    @Test
    void should_return_200_when_report_ok() throws Exception {
        var token = signup_then_login("admin", "123", "ADMIN");
        var profile =
                "{" +
                    "\"name\": \"admin\"," +
                    "\"age\":45," +
                    "\"gender\":\"male\"," +

                    "\"latitude\":12.2," +
                    "\"longitude\":40.2," +
                    "\"base\":\"base\"," +

                    "\"inventory\": [" +
                        "{" +
                            "\"name\": \"doritos\"," +
                            "\"price\":2" +
                        "}" +
                    "]" +
                "}";
        create_profile(profile, token);

        rebelRepository.save(new Rebel());

        String requestBody = "{" + "\"reportedId\":2" + "}";

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
        var token = signup_then_login("admin", "123", "ADMIN");
        resistanceUserRepository.findUserBy("admin").orElseThrow().setRebel(rebelLeft);
        resistanceUserRepository.findUserBy("admin").orElseThrow().setLocation(locationLeft);

        String requestBody = "{" +
                                "\"latitude\":109.23," +
                                "\"longitude\":-2346," +
                                "\"base\":\"new-base\"" +
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
        var token = signup_then_login("admin", "123", "ADMIN");
        rebelLeft.setInventory(inventoryLeft);
        rebelRight.setInventory(inventoryRight);
        inventoryRepository.save(inventoryLeft);
        inventoryRepository.save(inventoryRight);
        itemRepository.saveAll(Arrays.asList(food, water));
        rebelRepository.saveAll(Arrays.asList(rebelLeft, rebelRight));

        String requestBody = "{" +
                                "\"leftRebelId\":1," +
                                "\"leftItemId\":1," +
                                "\"rightRebelId\":2," +
                                "\"rightItemId\":2" +
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
        var token = signup_then_login("admin", "123", "ADMIN");
        String requestBody = "";
        mockMvc.perform(post("/rebel/report")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isMethodNotAllowed());
    }

    @Test
    void should_return_405_when_GET_report() throws Exception {
        var token = signup_then_login("admin", "123", "ADMIN");
        String requestBody = "";
        mockMvc.perform(get("/rebel/report")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isMethodNotAllowed());
    }


//    UPDATE LOCATION:
    @Test
    void should_return_405_when_POST_location_update() throws Exception {
        var token = signup_then_login("admin", "123", "ADMIN");
        String requestBody = "";
        mockMvc.perform(post("/rebel/update-location")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isMethodNotAllowed());
    }

    @Test
    void should_return_405_when_GET_location_update() throws Exception {
        var token = signup_then_login("admin", "123", "ADMIN");
        mockMvc.perform(get("/rebel/update-location")
                        .header("Authorization", "Bearer " + token)
                ).andExpect(status().isMethodNotAllowed());
    }


//    TRADE:
    @Test
    void should_return_405_when_POST_trade() throws Exception {
        var token = signup_then_login("admin", "123", "ADMIN");
        String requestBody = "";
        mockMvc.perform(post("/rebel/trade")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isMethodNotAllowed());
    }

    @Test
    void should_return_405_when_GET_trade() throws Exception {
        var token = signup_then_login("admin", "123", "ADMIN");
        mockMvc.perform(get("/rebel/trade")
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isMethodNotAllowed());
    }


    /* ------------------------------- 400 BAD REQUEST ---------------------------------*/

//    REPORT:
    @Test
    void should_return_400_when_invalid_report() throws Exception {
        var token = signup_then_login("admin", "123", "ADMIN");
        mockMvc.perform(patch("/rebel/report")
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isBadRequest());
    }

//    UPDATE LOCATION:
    @Test
    void should_return_400_when_invalid_location_update() throws Exception {
        var token = signup_then_login("admin", "123", "ADMIN");
        mockMvc.perform(patch("/rebel/update-location")
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isBadRequest());
    }

//    TRADE:
    @Test
    void should_return_400_when_invalid_trade() throws Exception {
        var token = signup_then_login("admin", "123", "ADMIN");
        mockMvc.perform(patch("/rebel/trade")
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("ADMIN should be able to delete users")
    void should_delete_user() throws Exception {
        var token = signup_then_login("admin", "123", "ADMIN");
        ResistanceUser toDelete = new ResistanceUser("username", "123", UserRole.USER);
        resistanceUserRepository.save(toDelete);

        String requestBody = "{\"id\":2}";

        mockMvc.perform(delete("/rebel/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .header("Authorization", "Bearer " + token)
                .content(requestBody)
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("USER should not be able to delete users")
    void should_not_delete_user() throws Exception {
        var token = signup_then_login( "user", "456", "USER");

        String requestBody = "{\"id\":2}";
        mockMvc.perform(delete("/rebel/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .header("Authorization", "Bearer " + token)
                .content(requestBody)
        ).andExpect(status().isForbidden());
    }
}