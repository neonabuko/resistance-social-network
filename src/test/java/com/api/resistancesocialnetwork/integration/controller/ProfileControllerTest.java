package com.api.resistancesocialnetwork.integration.controller;

import com.api.resistancesocialnetwork.usecase.ProfileUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProfileControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProfileUseCase signup;
    private String token;

    private void signup_then_login(String username, String password, String role) throws Exception {
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
        token = mvcResult.getResponse().getContentAsString();
    }


    /* ----------------------------  200 OK  -------------------------------*/

    @Test
    void should_return_200_when_get_homePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }


    /* ---------------------------  201 CREATED  ----------------------------*/
    @Test
    void should_return_201_when_signup_ok() throws Exception {
        signup_then_login("admin", "123", "ADMIN");
        String requestBody =
                "{" +
                    "\"name\": \"marcio\"," +
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
        mockMvc.perform(MockMvcRequestBuilders
                .post("/profile")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isCreated());
    }


    /* ----------------------------  400 BAD REQUEST  -------------------------------*/
    @Test
    void should_should_return_400_when_invalid_signup() throws Exception {
        signup_then_login("admin", "123", "ADMIN");
        mockMvc.perform(post("/profile")
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isBadRequest());
    }


    /* ----------------------------  405 METHOD NOT ALLOWED  -------------------------------*/
    @Test
    void should_return_405_when_PATCH_main_page() throws Exception {
        signup_then_login("admin", "123", "ADMIN");
        String requestBody = "";
        mockMvc.perform(patch("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isMethodNotAllowed());
    }


    @Test
    void should_return_405_when_POST_main_page() throws Exception {
        signup_then_login("admin", "123", "ADMIN");
        String requestBody = "";
        mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isMethodNotAllowed());
    }


    @Test
    void should_return_405_when_PATCH_signup() throws Exception {
        signup_then_login("admin", "123", "ADMIN");
        String requestBody = "";
        mockMvc.perform(patch("/profile")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isMethodNotAllowed());
    }

    @Test
    void should_return_405_when_GET_signup() throws Exception {
        signup_then_login("admin", "123", "ADMIN");
        String requestBody = "";
        mockMvc.perform(get("/profile")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isMethodNotAllowed());
    }
}
