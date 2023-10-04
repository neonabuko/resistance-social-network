package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.RebelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ComponentScan(basePackages = {"com.api.resistancesocialnetwork" })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class SignUpControllerTest {

    @Autowired
    private RebelRepository rebelRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Test
    void should_return_status_200_when_hit_main_page() {
        String url = "http://localhost:8080/";
        ResponseEntity<String> actualResponse = restTemplate.getForEntity(url, String.class);
        boolean isOk = actualResponse.getStatusCode().isSameCodeAs(HttpStatus.valueOf(200));
        assertTrue(isOk);
    }
}