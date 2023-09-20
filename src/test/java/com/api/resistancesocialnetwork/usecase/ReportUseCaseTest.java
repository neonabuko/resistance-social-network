package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.RebelRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReportUseCaseTest {
    @Autowired
    private RebelRepository rebelRepository;
    @Autowired
    private ReportUseCase reportUseCase;

    @Test
    void should_report_a_rebel() throws Exception {

        Rebel rebel1 = new Rebel("zezinho",1,"masculino");
        Rebel rebel2 = new Rebel("pedrinho",1,"feminino");
        rebelRepository.save(rebel1);
        rebelRepository.save(rebel2);

        reportUseCase.handle(rebel1.getId(), rebel2.getId());

        Assertions.assertEquals(rebel1.getId(), rebelRepository.findById(rebel2.getId()).orElseThrow().getReportCounter());
    }
}
