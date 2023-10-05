package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.usecase.SignupUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class SignUpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SignupUseCase signupUseCase;

    @Test
    void should_return_200() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    void should_return_405_when_POST_main_page() throws Exception {
        String requestBody = "";
        mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().is(405));
    }

    @Test
    void should_return_405_when_PATCH_main_page() throws Exception {
        String requestBody = "";
        mockMvc.perform(patch("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().is(405));
    }


    @Test
    void should_return_405_when_PATCH_signup() throws Exception {
        String requestBody = "";
        mockMvc.perform(patch("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().is(405));
    }

    @Test
    void should_return_405_when_GET_signup() throws Exception {
        String requestBody = "";
        mockMvc.perform(get("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().is(405));
    }

    @Test
    void should_return_201_when_signup_ok() throws Exception {
        String requestBody =
                "{" +
                    "\"signup\": {" +
                        "\"rebel\": {" +
                            "\"name\": \"marcio\"," +
                            "\"age\":45," +
                            "\"gender\":\"male\"" +
                            "}," +
                        "\"location\": {" +
                            "\"latitude\":12.2," +
                            "\"longitude\":40.2," +
                            "\"base\":\"base\"" +
                            "}," +
                        "\"inventory\": {" +
                            "\"items\": [" +
                                "{" +
                                    "\"name\": \"doritos\"," +
                                    "\"price\":2" +
                                "}" +
                            "]" +
                        "}" +
                    "}" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders
                .post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isCreated());
    }
}
