package com.api.resistancesocialnetwork.unit.usecase;

import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.facade.ReportFacade;
import com.api.resistancesocialnetwork.repository.repositoriesinmemory.RebelRepositoryInMemory;
import com.api.resistancesocialnetwork.rules.ReportRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import com.api.resistancesocialnetwork.usecase.ReportUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void should_throw_exception_when_reporting_rebel_not_found() {
        ReportFacade reportFacade = new ReportFacade(10, 2);
        Exception e = assertThrows(ResistanceSocialNetworkException.class, () ->
                new ReportUseCase( rebelRepoInMem, new ReportRules() )
                        .handle(reportFacade)
                );
        assertTrue(e.getMessage().contains("reporting rebel not found"));
    }


    @Test
    void should_throw_exception_when_reported_rebel_not_found(){
        Rebel zezinho = new Rebel("zezinho", 18, "masculino");
        zezinho.setId(1);
        rebelRepoInMem.save(zezinho);
        ReportFacade reportFacade = new ReportFacade(1, 55);
        Exception e = assertThrows(ResistanceSocialNetworkException.class, () ->
                new ReportUseCase( rebelRepoInMem, new ReportRules() )
                    .handle(reportFacade));
        assertTrue(e.getMessage().contains("reported rebel not found"));

    }
}