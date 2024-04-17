package com.api.resistancesocialnetwork.unit.usecase;

import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.repository.repositoriesinmemory.RebelRepositoryInMemory;
import com.api.resistancesocialnetwork.usecase.statistics.AlliesTraitorsPercentagesUseCase;
import org.junit.jupiter.api.Test;

import java.text.NumberFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlliesTraitorsPercentagesUseCaseTest {
    private final RebelRepositoryInMemory repository = new RebelRepositoryInMemory();
    private final AlliesTraitorsPercentagesUseCase alliesTraitorsPercentages = new AlliesTraitorsPercentagesUseCase(repository);

    @Test
    void should_return_percentages() {
        Rebel luke = new Rebel("luke", 28, "male");
        repository.save(luke);

        var actualPercents = alliesTraitorsPercentages.handle();
        NumberFormat toPercent = NumberFormat.getPercentInstance();

        String expectedAlliesPercents = toPercent.format(1.0);
        String expectedTraitorsPercents = toPercent.format(0.0);

        String actualAlliesPercents = actualPercents.allies();
        String actualTraitorsPercents = actualPercents.traitors();

        assertEquals(expectedAlliesPercents, actualAlliesPercents);
        assertEquals(expectedTraitorsPercents, actualTraitorsPercents);
    }
}