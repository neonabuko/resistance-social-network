package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.usecase.AlliesTraitorsPercentagesUseCase;
import com.api.resistancesocialnetwork.usecase.ItemAveragesPerRebelUseCase;
import com.api.resistancesocialnetwork.usecase.ShowAlliesUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

@RestController
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
            if (s.startsWith("Location")) allies.set(allies.indexOf(s), "\t" + s);
            else if (s.startsWith("Inventory")) allies.set(allies.indexOf(s), "\t" + s + "\n");
        }
        return ResponseEntity.ok(String.join("\n", allies));
    }

    @GetMapping("/allies-traitors-percentages")
    public ResponseEntity<String> showAlliesTraitorsPercentages() {
        List<Double> decimalsList = alliesTraitorsPercentagesUseCase.handle();
        NumberFormat decimalToPercentage = NumberFormat.getPercentInstance();
        String percentages = "Allies: " + decimalToPercentage.format(decimalsList.get(0)) + ", " +
                             "Traitors: " + decimalToPercentage.format(decimalsList.get(1));

        return ResponseEntity.ok(percentages);
    }

    @GetMapping("/item-averages")
    public ResponseEntity<String> showItemAverages() {
        Map<String, Double> averagesMap = itemAveragesPerRebelUseCase.handle();
        String response = "Average of each item per rebel: \n" + averagesMap.toString();
        return ResponseEntity.ok(response);
    }
}
