package com.api.resistancesocialnetwork.integration.infra.security;

import com.api.resistancesocialnetwork.entity.User;
import com.api.resistancesocialnetwork.enums.UserRole;
import com.api.resistancesocialnetwork.infra.security.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class TokenServiceTest {

    @Autowired
    TokenService tokenService;

    @Test
    void should_create_token() {
        String encryptedPassword = new BCryptPasswordEncoder().encode("sees");
        User user = new User("JooJ", encryptedPassword, UserRole.ADMIN);
        String token = tokenService.generateToken(user);
        String decryptedToken = tokenService.getUsernameFromToken(token).orElseThrow();

        assertNotNull(token);
    }

    @Test
    void should_contain_username_in_validate_token() {
        String encryptedPassword = new BCryptPasswordEncoder().encode("sees");
        User user = new User("JooJ", encryptedPassword, UserRole.ADMIN);
        String token = tokenService.generateToken(user);
        String validateToken = tokenService.getUsernameFromToken(token).orElseThrow();

        assertEquals("JooJ", validateToken);
    }

    @Test
    void should_return_OptionalEmpty_when_username_null() {
        String encryptedPassword = new BCryptPasswordEncoder().encode("sees");
        User user = new User(null, encryptedPassword, UserRole.ADMIN);
        String token = tokenService.generateToken(user);
        Optional<String> validateToken = tokenService.getUsernameFromToken(token);

        assertEquals(Optional.empty(), validateToken);
    }
}