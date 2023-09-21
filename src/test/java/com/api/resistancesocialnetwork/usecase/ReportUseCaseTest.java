package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.RebelRepository;
import com.api.resistancesocialnetwork.repositories.RebelRepositoryInMem;
import com.api.resistancesocialnetwork.rules.ReportRules;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

public class ReportUseCaseTest {
    private RebelRepositoryInMem rebelRepoInMem;

    @BeforeEach
    void setUp() {
        this.rebelRepoInMem = new RebelRepositoryInMem();
    }

    @Test
    void should_report_a_rebel() throws Exception {

        Rebel rebel1 = new Rebel("zezinho",1,"masculino");
        Rebel rebel2 = new Rebel("pedrinho",1,"feminino");

        rebelRepoInMem.save(rebel1);
        rebelRepoInMem.save(rebel2);

        ReportRules reportRules = new ReportRules(rebelRepoInMem);
        ReportUseCase reportUseCase = new ReportUseCase(rebelRepoInMem, reportRules);

        reportUseCase.handle(rebel1.getId(), rebel2.getId());

        Assertions.assertEquals(1, rebelRepoInMem.findById(rebel2.getId()).orElseThrow().getReportCounter());
    }
}
