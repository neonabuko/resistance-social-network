package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.repositories.LocationRepository;
import com.api.resistancesocialnetwork.repositories.RebelRepository;
import com.api.resistancesocialnetwork.usecase.ShowAlliesUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainPageController {
    private final ShowAlliesUseCase showAlliesUseCase;
    private final RebelRepository rebelRepository;
    private final LocationRepository locationRepo;

    @Autowired
    public MainPageController(ShowAlliesUseCase showAlliesUseCase, RebelRepository rebelRepository, LocationRepository locationRepo) {
        this.showAlliesUseCase = showAlliesUseCase;
        this.rebelRepository = rebelRepository;
        this.locationRepo = locationRepo;
    }

    @GetMapping("/")
    public ResponseEntity<String> displayMainPage() {
        return ResponseEntity.ok("Welcome to the Star Wars Resistance Social Network!");
    }

    @GetMapping("/show-allies")
    public ResponseEntity<String> getAllRebels() {
        List<String> allies = showAlliesUseCase.handle();
        return ResponseEntity.ok(String.join("\n", allies));
    }
}
