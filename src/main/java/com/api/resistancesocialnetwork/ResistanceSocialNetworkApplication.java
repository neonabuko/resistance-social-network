package com.api.resistancesocialnetwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {
        "com.api.resistancesocialnetwork.model"
})
@EnableJpaRepositories(basePackages = {
        "com.api.resistancesocialnetwork.repositories"
})
public class ResistanceSocialNetworkApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResistanceSocialNetworkApplication.class, args);
    }
}
