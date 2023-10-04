package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.usecase.SignupUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignUpController.class)
@AutoConfigureMockMvc
class SignUpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SignupUseCase signupUseCase;

    @Test
    void get_home_page_should_return_200() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());
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
