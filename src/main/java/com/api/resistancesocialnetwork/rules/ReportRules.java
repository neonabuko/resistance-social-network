package com.api.resistancesocialnetwork.rules;


import com.api.resistancesocialnetwork.repositories.RebelRepository;

public class ReportRules {
    private final RebelRepository rebelRepo;

    public ReportRules(RebelRepository rebelRepo) {
        this.rebelRepo = rebelRepo;
    }

    public void handle(Integer sourceId, Integer targetId) throws Exception {
        if (!rebelRepo.existsById(sourceId)) throw new Exception("source rebel not found");
        if (!rebelRepo.existsById(targetId)) throw new Exception("target rebel not found");
        if (rebelRepo.findById(sourceId).get().getReportedRebels().contains(targetId)) throw new Exception("rebel already reported") ;
    }
}
