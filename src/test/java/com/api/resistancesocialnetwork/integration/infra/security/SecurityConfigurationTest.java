package com.api.resistancesocialnetwork.integration.infra.security;

import com.api.resistancesocialnetwork.infra.security.SecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class SecurityConfigurationTest {

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Test
    void should_return_PasswordEncoder() {
        PasswordEncoder passwordEncoder = securityConfiguration.getPasswordEncoder();

        assertNotNull(passwordEncoder);
    }

    @Test
    void should_return_AuthenticationManager() throws Exception {
        AuthenticationManager authenticationManager = securityConfiguration.getAuthManager(
                new AuthenticationConfiguration()
        );

        assertNotNull(authenticationManager);
    }
}