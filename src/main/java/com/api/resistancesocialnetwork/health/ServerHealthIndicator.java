package com.api.resistancesocialnetwork.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatusCode;
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
        if (isExternalServiceHealthy()) {
            return Health.up().build();
        } else {
            return Health.down().withDetail("Reason", "Health check failed").build();
        }
    }

    private boolean isExternalServiceHealthy() {
        try {
            String externalServiceUrl = "http://localhost:8080";
            ResponseEntity<String> response = restTemplate.getForEntity(externalServiceUrl, String.class);
            return response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return false;
        }
    }
}
