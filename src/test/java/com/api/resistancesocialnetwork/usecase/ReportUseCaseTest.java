package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.RebelRepositoryInMemory;
import com.api.resistancesocialnetwork.request.facade.ReportFacade;
import com.api.resistancesocialnetwork.rules.ReportRules;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
public class ReportUseCaseTest {
    private final RebelRepositoryInMemory rebelRepoInMem = new RebelRepositoryInMemory();

    @Test
    void should_increase_reportCounter_of_reported_rebel_by_one() {
        Rebel zezinho = new Rebel("zezinho", 18, "masculino");
        Rebel pedrinho = new Rebel("pedrinho", 18, "feminino");
        zezinho.setId(1);
        pedrinho.setId(2);
        rebelRepoInMem.saveAll(Arrays.asList(zezinho, pedrinho));

        Assertions.assertEquals(0, pedrinho.getReportCounter());

        ReportFacade reportFacade = new ReportFacade(1, 2);
        new ReportUseCase( rebelRepoInMem, new ReportRules() )
                .handle(reportFacade);

        Assertions.assertEquals(1, pedrinho.getReportCounter());
    }
}