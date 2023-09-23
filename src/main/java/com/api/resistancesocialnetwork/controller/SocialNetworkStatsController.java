package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.usecase.AlliesTraitorsPercentagesUseCase;
import com.api.resistancesocialnetwork.usecase.ItemAveragesPerRebelUseCase;
import com.api.resistancesocialnetwork.usecase.ShowAlliesUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/alliance-stats")
public class SocialNetworkStatsController {
    private final ShowAlliesUseCase showAlliesUseCase;
    private final AlliesTraitorsPercentagesUseCase alliesTraitorsPercentagesUseCase;
    private final ItemAveragesPerRebelUseCase itemAveragesPerRebelUseCase;

    public SocialNetworkStatsController(ShowAlliesUseCase showAlliesUseCase,
                                        AlliesTraitorsPercentagesUseCase alliesTraitorsPercentagesUseCase,
                                        ItemAveragesPerRebelUseCase itemAveragesPerRebelUseCase) {
        this.showAlliesUseCase = showAlliesUseCase;
        this.alliesTraitorsPercentagesUseCase = alliesTraitorsPercentagesUseCase;
        this.itemAveragesPerRebelUseCase = itemAveragesPerRebelUseCase;
    }

    @GetMapping("/")
    public ResponseEntity<String> displayMainPage() {
        return ResponseEntity.ok("Welcome to the Star Wars Resistance Social Network.");
    }

    @GetMapping("/show-allies")
    public ResponseEntity<String> showAllAllies() {
        List<String> allies = showAlliesUseCase.handle();
        for (String s : allies) {
            if (s.startsWith("Inventory")) allies.set(allies.indexOf(s), s + "\n" + "─".repeat(50) + "¬");
        }
        return ResponseEntity.ok(String.join("\n", allies));
    }

    @GetMapping("/show-allies-traitors-percentages")
    public ResponseEntity<String> showAlliesTraitorsPercentages() {
        List<Double> decimalsList = alliesTraitorsPercentagesUseCase.handle();
        NumberFormat decimalToPercentage = NumberFormat.getPercentInstance();
        String percentages = "Allies: " + decimalToPercentage.format(decimalsList.get(0)) + ", " +
                             "Traitors: " + decimalToPercentage.format(decimalsList.get(1));
        return ResponseEntity.ok(percentages);
    }

    @GetMapping("/show-average-number-items")
    public ResponseEntity<String> showItemAverages() {
        Map<String, Double> averagesMap = itemAveragesPerRebelUseCase.handle();
        String response = "Average number of items per rebel: \n" + averagesMap.toString()
                .replace("{", "")
                .replace("}", "");
        return ResponseEntity.ok(response);
    }
}
