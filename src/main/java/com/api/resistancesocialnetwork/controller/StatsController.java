package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.usecase.statistics.AlliesTraitorsPercentagesUseCase;
import com.api.resistancesocialnetwork.usecase.statistics.ItemAveragesPerRebelUseCase;
import com.api.resistancesocialnetwork.usecase.statistics.ShowAlliesUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stats")
public class StatsController {
    private final ShowAlliesUseCase showAlliesUseCase;
    private final AlliesTraitorsPercentagesUseCase alliesTraitorsPercentagesUseCase;
    private final ItemAveragesPerRebelUseCase itemAveragesPerRebelUseCase;
    private final NumberFormat decimalToPercentage = NumberFormat.getPercentInstance();

    public StatsController(ShowAlliesUseCase showAlliesUseCase,
                           AlliesTraitorsPercentagesUseCase alliesTraitorsPercentagesUseCase,
                           ItemAveragesPerRebelUseCase itemAveragesPerRebelUseCase) {
        this.showAlliesUseCase = showAlliesUseCase;
        this.alliesTraitorsPercentagesUseCase = alliesTraitorsPercentagesUseCase;
        this.itemAveragesPerRebelUseCase = itemAveragesPerRebelUseCase;
    }

    @GetMapping("/show-allies")
    public ResponseEntity<String> showAllAllies() {
        List<String> allies = showAlliesUseCase.handle();

        if (allies.isEmpty()) return ResponseEntity.status(204).body("No rebels to show");

        return ResponseEntity.status(200).body(String.join("\n", allies));
    }

    @GetMapping("/show-allies-traitors-percentages")
    public ResponseEntity<String> showAlliesTraitorsPercentages() {
        List<Double> decimalsList = alliesTraitorsPercentagesUseCase.handle();

        if (decimalsList.isEmpty()) return ResponseEntity.status(204).body("No percentages of allies/traitors to show");

        String percentages = "Allies: " + decimalToPercentage.format(decimalsList.get(0)) + ", " +
                             "Traitors: " + decimalToPercentage.format(decimalsList.get(1));
        return ResponseEntity.status(200).body("Percentages of allies vs. traitors:\n\n" + percentages.replace(", ", "\n"));
    }

    @GetMapping("/show-average-number-items")
    public ResponseEntity<String> showItemAverages() {
        Map<String, Double> averagesMap = itemAveragesPerRebelUseCase.handle();

        if (averagesMap.isEmpty()) return ResponseEntity.status(204).body("No average number of items per rebel to show");

        String response = "Average number of items per rebel: \n\n" +
                averagesMap.toString()
                        .replace("{", "")
                        .replace("}", "")
                        .replace("=", ": ")
                        .replace(", ", "\n");

        return ResponseEntity.status(200).body(response);
    }
}