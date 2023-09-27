package com.api.resistancesocialnetwork.rules;


import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import org.springframework.stereotype.Component;

@Component
public class ReportRules {
    public void handle(Rebel sourceRebel, Rebel targetRebel) throws ResistanceSocialNetworkException {
        assert_not_self_report(sourceRebel, targetRebel);
        assert_target_not_already_reported_by_source(sourceRebel, targetRebel);
    }

    private void assert_not_self_report(Rebel sourceRebel, Rebel targetRebel) throws ResistanceSocialNetworkException {
        if (sourceRebel.getId().equals(targetRebel.getId()))
            throw new ResistanceSocialNetworkException("can not report yourself");
    }

    private void assert_target_not_already_reported_by_source(Rebel sourceRebel, Rebel targetRebel) throws ResistanceSocialNetworkException {
        if (sourceRebel.getReportedRebels().contains(targetRebel.getId()))
            throw new ResistanceSocialNetworkException("rebel already reported");
    }
}
