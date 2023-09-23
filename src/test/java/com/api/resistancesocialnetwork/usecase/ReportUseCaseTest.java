package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.RebelRepositoryInMemory;
import com.api.resistancesocialnetwork.rules.ReportRules;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ReportUseCaseTest {
    private final RebelRepositoryInMemory rebelRepoInMem = new RebelRepositoryInMemory();

    @Test
    void should_report_a_rebel() throws Exception {
        Rebel rebel1 = new Rebel("zezinho", 1, "masculino");
        Rebel rebel2 = new Rebel("pedrinho", 1, "feminino");
        rebel1.setId(1);
        rebel2.setId(2);
        rebelRepoInMem.saveInMem(rebel1);
        rebelRepoInMem.saveInMem(rebel2);

        ReportRules reportRules = new ReportRules(rebelRepoInMem);
        ReportUseCase reportUseCase = new ReportUseCase(rebelRepoInMem, reportRules);

        reportUseCase.handle(rebel1.getId(), rebel2.getId());

        Assertions.assertEquals(1, rebelRepoInMem.findById(rebel2.getId()).orElseThrow().getReportCounter());
    }
}
