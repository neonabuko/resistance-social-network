package com.api.resistancesocialnetwork.unit.usecase;

import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.RebelRepositoryInMemory;
import com.api.resistancesocialnetwork.usecase.statistics.AlliesTraitorsPercentagesUseCase;
import org.junit.jupiter.api.Test;

import java.text.NumberFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlliesTraitorsPercentagesUseCaseTest {
    private final RebelRepositoryInMemory repository = new RebelRepositoryInMemory();
    private final AlliesTraitorsPercentagesUseCase alliesTraitorsPercentages = new AlliesTraitorsPercentagesUseCase(repository);

    @Test
    void should_return_percentages() {
        Rebel luke = new Rebel("luke", 28, "male");
        repository.save(luke);

        List<String> actualPercents = alliesTraitorsPercentages.handle();
        NumberFormat toPercent = NumberFormat.getPercentInstance();

        String expectedAlliesPercents = toPercent.format(1.0);
        String expectedTraitorsPercents = toPercent.format(0.0);

        String actualAlliesPercents = actualPercents.get(0);
        String actualTraitorsPercents = actualPercents.get(1);

        assertEquals(expectedAlliesPercents, actualAlliesPercents);
        assertEquals(expectedTraitorsPercents, actualTraitorsPercents);
    }
}