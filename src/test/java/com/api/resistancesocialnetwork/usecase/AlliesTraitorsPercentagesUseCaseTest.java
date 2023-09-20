package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.RebelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        Rebel luke = new Rebel("luke", 28, "male");
        luke.setReportCounter(0);
        rebelRepo.save(luke);

        List<Double> actualDecimals = alliesTraitorsPercentagesUseCase.handle();

        Double actualAlliesDecimal = actualDecimals.get(0);
        Double actualTraitorsDecimal = actualDecimals.get(1);

        Double expectedAlliesDecimal = 1.0;
        Double expectedTraitorsDecimal = 0.0;

        assertEquals(expectedAlliesDecimal, actualAlliesDecimal);
        assertEquals(expectedTraitorsDecimal, actualTraitorsDecimal);
    }
}