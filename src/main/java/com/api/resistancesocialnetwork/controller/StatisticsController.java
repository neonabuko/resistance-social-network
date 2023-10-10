package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.usecase.statistics.AlliesTraitorsPercentagesUseCase;
import com.api.resistancesocialnetwork.usecase.statistics.AlliesUseCase;
import com.api.resistancesocialnetwork.usecase.statistics.ItemAveragesUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stats")
public class StatisticsController {
    private final AlliesUseCase allies;
    private final AlliesTraitorsPercentagesUseCase alliesTraitorsPercentages;
    private final ItemAveragesUseCase itemAverages;

    public StatisticsController(AlliesUseCase allies,
                                AlliesTraitorsPercentagesUseCase alliesTraitorsPercentages,
                                ItemAveragesUseCase itemAverages) {
        this.allies = allies;
        this.alliesTraitorsPercentages = alliesTraitorsPercentages;
        this.itemAverages = itemAverages;
    }

    @GetMapping("/allies")
    public ResponseEntity<String> allies() {
        List<String> allies = this.allies.handle();
        if (allies.isEmpty()) return ResponseEntity.status(204).body("No rebels to show");
        return ResponseEntity.status(200).body(String.join("\n", allies));
    }

    @GetMapping("/allies-traitors-percentages")
    public ResponseEntity<String> alliesTraitorsPercentages() {
        List<String> percentages = alliesTraitorsPercentages.handle();
        if (percentages.isEmpty()) return ResponseEntity.status(204).body("No percentages to show");
        return ResponseEntity.status(200).body("Allies: " + percentages.get(0) + " Traitors: " + percentages.get(1));
    }

    @GetMapping("/item-averages")
    public ResponseEntity<String> itemAverages() {
        Map<String, Double> averages = itemAverages.handle();
        if (averages.isEmpty()) return ResponseEntity.status(204).body("No averages to show");
        String response = averages.toString().replace(", ", "\n");
        return ResponseEntity.status(200).body(response);
    }

}