package com.api.resistancesocialnetwork.rules;


import com.api.resistancesocialnetwork.model.Rebel;
import org.springframework.stereotype.Component;

@Component
public class ReportRules {
    public void handle(Rebel sourceRebel, Rebel targetRebel) throws IllegalArgumentException {
        assert_not_self_report(sourceRebel, targetRebel);
        assert_target_not_already_reported_by_source(sourceRebel, targetRebel);
    }

    private void assert_not_self_report(Rebel sourceRebel, Rebel targetRebel) {
        if (sourceRebel.getId().equals(targetRebel.getId()))
            throw new IllegalArgumentException("can not report yourself");
    }

    private void assert_target_not_already_reported_by_source(Rebel sourceRebel, Rebel targetRebel) {
        if (sourceRebel.getReportedRebels().contains(targetRebel.getId()))
            throw new IllegalArgumentException("rebel already reported");
    }
}
