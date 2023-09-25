package com.api.resistancesocialnetwork.rules;


import com.api.resistancesocialnetwork.model.Rebel;
import org.springframework.stereotype.Component;

@Component
public class ReportRules {
    public void handle(Rebel sourceRebel, Rebel targetRebel) throws IllegalArgumentException {
        if (sourceRebel.getId().equals(targetRebel.getId())) throw new IllegalArgumentException("can not report yourself");

        if (sourceRebel.getReportedRebels().contains(targetRebel.getId()))
            throw new IllegalArgumentException("rebel already reported");
    }
}
