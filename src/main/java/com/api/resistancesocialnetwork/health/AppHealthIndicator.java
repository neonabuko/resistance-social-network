package com.api.resistancesocialnetwork.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AppHealthIndicator implements HealthIndicator {

    private final RestTemplate restTemplate;

    public AppHealthIndicator(RestTemplate restTemplate) {
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
            restTemplate.getForObject(externalServiceUrl, String.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
