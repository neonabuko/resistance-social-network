package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.RebelRepositoryInMemory;
import com.api.resistancesocialnetwork.request.DTO.ReportDTO;
import com.api.resistancesocialnetwork.rules.ReportRules;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ReportUseCaseTest {
    private final RebelRepositoryInMemory rebelRepoInMem = new RebelRepositoryInMemory();

    @Test
    void reportCounter_should_increase_by_one_after_report() throws Exception {
        Rebel rebel1 = new Rebel("zezinho", 18, "masculino");
        Rebel rebel2 = new Rebel("pedrinho", 18, "feminino");
        rebel1.setId(1);
        rebel2.setId(2);
        rebelRepoInMem.saveInMem(rebel1);
        rebelRepoInMem.saveInMem(rebel2);

        ReportRules reportRules = new ReportRules();
        ReportUseCase reportUseCase = new ReportUseCase(rebelRepoInMem, reportRules);

        reportUseCase.handle(reportDTO);

        Assertions.assertEquals(1, rebel2.getReportCounter());
    }
}
