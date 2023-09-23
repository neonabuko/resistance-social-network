package com.api.resistancesocialnetwork.rules;


import com.api.resistancesocialnetwork.repositories.interfacerepositories.RebelRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReportRules {
    private final RebelRepository rebelRepo;

    public ReportRules(RebelRepository rebelRepo) {
        this.rebelRepo = rebelRepo;
    }

    public void handle(Integer sourceId, Integer targetId) throws IllegalArgumentException {
        Optional<Integer> optionalSourceId = Optional.ofNullable(sourceId);
        Optional<Integer> optionalTargetId = Optional.ofNullable(targetId);

        if (!rebelRepo.existsById(optionalSourceId.orElseThrow(
                () -> new IllegalArgumentException("must provide source rebel id")))
        ) throw new IllegalArgumentException("source rebel not found");

        if (!rebelRepo.existsById(optionalTargetId.orElseThrow(
                () -> new IllegalArgumentException("must provide target rebel id")))
        ) throw new IllegalArgumentException("target rebel not found");

        if (targetId.equals(sourceId)) throw new IllegalArgumentException("can not report yourself");

        if (rebelRepo.findById(sourceId).get().getReportedRebels().contains(targetId))
            throw new IllegalArgumentException("rebel already reported");
    }
}
