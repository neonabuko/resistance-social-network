package com.api.resistancesocialnetwork.unit.usecase;

import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.entity.ResistanceUser;
import com.api.resistancesocialnetwork.facade.ReportFacade;
import com.api.resistancesocialnetwork.repository.repositoriesinmemory.RebelRepositoryInMemory;
import com.api.resistancesocialnetwork.rules.ReportRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceException;
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
        var zezinhoUser = new ResistanceUser();
        Rebel zezinho = new Rebel("zezinho", 18, "masculino");
        zezinhoUser.setRebel(zezinho);
        Rebel pedrinho = new Rebel("pedrinho", 18, "feminino");
        zezinho.setId(1);
        pedrinho.setId(2);
        rebelRepoInMem.saveAll(Arrays.asList(zezinho, pedrinho));

        Assertions.assertEquals(0, pedrinho.getReportCounter());

        ReportFacade reportFacade = new ReportFacade(2);
        new ReportUseCase(rebelRepoInMem, new ReportRules()).handle(reportFacade, zezinhoUser);

        Assertions.assertEquals(1, pedrinho.getReportCounter());
    }

    @Test
    void should_throw_exception_when_reporting_rebel_not_found() {
        ReportFacade reportFacade = new ReportFacade(2);
        Exception e = assertThrows(ResistanceException.class, () ->
                new ReportUseCase( rebelRepoInMem, new ReportRules() ).handle(reportFacade, new ResistanceUser()));
        assertTrue(e.getMessage().contains("Reporting user must set rebel profile first."));
    }


    @Test
    void should_throw_exception_when_reported_rebel_not_found(){
        var zezinhoUser = new ResistanceUser();
        Rebel zezinho = new Rebel("zezinho", 18, "masculino");
        zezinhoUser.setRebel(zezinho);
        zezinho.setId(1);
        rebelRepoInMem.save(zezinho);
        ReportFacade reportFacade = new ReportFacade(55);
        Exception e = assertThrows(ResistanceException.class, () ->
                new ReportUseCase( rebelRepoInMem, new ReportRules() ).handle(reportFacade, zezinhoUser));
        assertTrue(e.getMessage().contains("Rebel to be reported not found."));

    }
}