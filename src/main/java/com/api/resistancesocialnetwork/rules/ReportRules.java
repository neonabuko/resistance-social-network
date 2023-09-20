package com.api.resistancesocialnetwork.rules;


import com.api.resistancesocialnetwork.repositories.RebelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ReportRules {
    private final RebelRepository rebelRepo;

    @Autowired
    public ReportRules(RebelRepository rebelRepo) {
        this.rebelRepo = rebelRepo;
    }

    public void handle(Integer sourceId, Integer targetId) throws Exception {
        Optional<Integer> optionalSourceId = Optional.ofNullable(sourceId);
        Optional<Integer> optionalTargetId = Optional.ofNullable(targetId);

        if (!rebelRepo.existsById(optionalSourceId.orElseThrow(
                () -> new Exception("must provide source rebel id"))
        )) throw new Exception("source rebel not found");

        if (!rebelRepo.existsById(optionalTargetId.orElseThrow(
                () -> new Exception(("must provide target rebel id")))
        )) throw new Exception("target rebel not found");

        if (rebelRepo.findById(sourceId).get().getReportedRebels().contains(targetId))
            throw new Exception("rebel already reported") ;
    }
}
