package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.RebelRepositoryInMemory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlliesTraitorsPercentagesUseCaseTest {
    private final RebelRepositoryInMemory rebelRepoInMem = new RebelRepositoryInMemory();
    private final AlliesTraitorsPercentagesUseCase alliesTraitorsPercentagesUseCase = new AlliesTraitorsPercentagesUseCase(rebelRepoInMem);

    @Test
    void should_return_percentages_string() {
        Rebel luke = new Rebel("luke", 28, "male");
        rebelRepoInMem.saveInMem(luke);

        List<Double> actualDecimals = alliesTraitorsPercentagesUseCase.handle();

        Double expectedAlliesDecimal = 1.0;
        Double expectedTraitorsDecimal = 0.0;

        Double actualAlliesDecimal = actualDecimals.get(0);
        Double actualTraitorsDecimal = actualDecimals.get(1);

        assertEquals(expectedAlliesDecimal, actualAlliesDecimal);
        assertEquals(expectedTraitorsDecimal, actualTraitorsDecimal);
    }
}