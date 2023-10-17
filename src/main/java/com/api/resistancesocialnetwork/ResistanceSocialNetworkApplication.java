package com.api.resistancesocialnetwork;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Resistance Social Network",
        version = "0.0.1",
        description = "Uma API para gerenciar rebeldes"
))
public class ResistanceSocialNetworkApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResistanceSocialNetworkApplication.class, args);
    }
}
