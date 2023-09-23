package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.usecase.ShowAlliesUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SocialNetworkStatsController {
    private final ShowAlliesUseCase showAlliesUseCase;

    public SocialNetworkStatsController(ShowAlliesUseCase showAlliesUseCase) {
        this.showAlliesUseCase = showAlliesUseCase;
    }

    @GetMapping("/")
    public ResponseEntity<String> displayMainPage() {
        return ResponseEntity.ok("Welcome to the Star Wars Resistance Social Network.");
    }

    @GetMapping("/show-allies")
    public ResponseEntity<String> showAllAllies() {
        List<String> allies = showAlliesUseCase.handle();
        for (String s : allies) {
            if (s.startsWith("Location")) allies.set(allies.indexOf(s), "\t" + s);
            else if (s.startsWith("Inventory")) allies.set(allies.indexOf(s), "\t" + s + "\n");
        }
        return ResponseEntity.ok(String.join("\n", allies));
    }
}
