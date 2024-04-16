package com.api.resistancesocialnetwork.integration.controller;


import com.api.resistancesocialnetwork.entity.ResistanceUser;
import com.api.resistancesocialnetwork.enums.UserRole;
import com.api.resistancesocialnetwork.repository.repositoryinterfaces.ResistanceUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ResistanceUserRepository repository;
    private String token;

    private void signup(String username, String password, String role) throws Exception {
        String requestBody = "{" +
                                 "\"username\":\"" + username + "\"," +
                                 "\"password\":\"" + password + "\"," +
                                 "\"role\":\"" + role + "\"" +
                             "}";

        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        );
    }

    @Test
    @DisplayName("should return status 200 when register ok")
    void should_return_200_when_register_user() throws Exception {
        String requestBody = "{\"username\":\"ok\"," +
                             "\"password\":\"alberto\"," +
                             "\"role\":\"ADMIN\"}";
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("should return status 409 (CONFLICT) when username already taken")
    void should_return_409_when_username_taken() throws Exception {
        repository.save(new ResistanceUser("LeeL", "alberto", UserRole.USER));

        String requestBody = "{\"username\":\"LeeL\"," +
                             "\"password\":\"alberto\"," +
                             "\"role\":\"ADMIN\"}";
        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isConflict());
    }

    @Test
    @DisplayName("should assign USER if role not provided")
    void should_assign_USER_if_role_not_provided() throws Exception {
        String requestBody = "{\"username\":\"LeeL2\"," +
                             "\"password\":\"alberto\"}";
        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isOk());

        Assertions.assertTrue(
                repository.findUserBy("LeeL2").orElseThrow().getAuthorities()
                        .contains(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    @Test
    @DisplayName("should not assign ADMIN if role not provided")
    void should_not_assign_ADMIN_if_role_not_provided() throws Exception {
        String requestBody = "{\"username\":\"not admin\"," +
                             "\"password\":\"alberto\"}";
        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isOk());

        Assertions.assertFalse(
                repository.findUserBy("not admin").orElseThrow().getAuthorities()
                        .contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );
    }

    @Test
    @DisplayName("should return status 200 when username valid")
    void should_return_200_when_login_user() throws Exception {
        signup("valid", "123", "USER");

        String requestBody = "{\"username\":\"valid\"," +
                             "\"password\":\"123\"}";
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("should return status 400 when password not provided")
    void should_return_400_when_password_not_provided_in_login() throws Exception {
        signup("not provided", "", "ADMIN");

        String requestBody = "{\"username\":\"LeeL\"}";
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should return status 400 when username not provided")
    void should_return_400_when_login_not_provided() throws Exception {
        signup("", "123", "ADMIN");

        String requestBody = "{\"password\":\"alberto\"}";
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should return status 400 when login format invalid")
    void should_return_400_when_login_invalid() throws Exception {
        String requestBody = "{\"username\":\"LeeL\"," +
                             "\"password\":\"alberto\"";
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isBadRequest());
    }
}