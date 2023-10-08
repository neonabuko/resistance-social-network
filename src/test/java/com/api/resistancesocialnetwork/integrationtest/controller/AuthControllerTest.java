package com.api.resistancesocialnetwork.integrationtest.controller;


import com.api.resistancesocialnetwork.repositories.repositoriesindatabase.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String token;

    @Autowired
    private UserRepository userRepository;

    void register() throws Exception {
        String requestBody = "{\"login\":\"LeeL\"," +
                "\"password\":\"alberto\"}";

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        );
    }


    @Test
    @DisplayName("should return status 200 when register ok")
    void should_return_200_when_register_user() throws Exception {
        String requestBody = "{\"login\":\"LeeL2\"," +
                            "\"password\":\"alberto\"," +
                                "\"role\":\"ADMIN\"}";
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("should assign role USER if role not provided")
    void should_assign_USER_if_role_not_provided() throws Exception {
        String requestBody = "{\"login\":\"LeeL2\"," +
                             "\"password\":\"alberto\"}";
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isOk());
        Assertions.assertTrue(
                userRepository.findByLogin("LeeL2").getAuthorities()
                        .contains(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    @Test
    @DisplayName("should not assign role ADMIN if role not provided")
    void should_not_assign_ADMIN_if_role_not_provided() throws Exception {
        String requestBody = "{\"login\":\"LeeL2\"," +
                             "\"password\":\"alberto\"}";
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isOk());
        Assertions.assertFalse(
                userRepository.findByLogin("LeeL2").getAuthorities()
                        .contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );
    }

    @Test
    @DisplayName("should return status 200 when login ok")
    void should_return_200_when_login_user() throws Exception {
        register();

        String requestBody = "{\"login\":\"LeeL\"," +
                                "\"password\":\"alberto\"}";
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isOk());
    }


}