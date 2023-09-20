package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.RebelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AlliesTraitorsPercentagesUseCaseTest {

    @Autowired
    private RebelRepository rebelRepo;
    @Autowired
    private AlliesTraitorsPercentagesUseCase alliesTraitorsPercentagesUseCase;

    @Test
    void should_return_percentages_string() {
        rebelRepo.save(new Rebel("luke", 28, "male"));

        List<String> actualPercentages = alliesTraitorsPercentagesUseCase.handle();

        NumberFormat percentagesFormat = NumberFormat.getPercentInstance();
        String alliesPercentage = percentagesFormat.format(1);
        String traitorsPercentage = percentagesFormat.format(0);
        List<String> expectedPercentages = new ArrayList<>(Arrays.asList(alliesPercentage, traitorsPercentage));
        assertEquals(expectedPercentages, actualPercentages);
    }

}