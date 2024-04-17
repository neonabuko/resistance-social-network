package com.api.resistancesocialnetwork.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ServerHealthIndicator implements HealthIndicator {

    private final RestTemplate restTemplate;

    public ServerHealthIndicator(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Health health() {
        return isExternalServiceHealthy() ? Health.up().build() :
                Health.down().withDetail("Reason", "Health check failed").build();
    }

    private boolean isExternalServiceHealthy() {
        String externalServiceUrl = "http://localhost:8080";
        ResponseEntity<String> response = restTemplate.getForEntity(externalServiceUrl, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }
}
