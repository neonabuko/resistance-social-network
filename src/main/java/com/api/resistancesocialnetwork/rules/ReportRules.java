package com.api.resistancesocialnetwork.rules;


import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import org.springframework.stereotype.Component;

@Component
public class ReportRules {
    public void handle(Rebel reporting, Rebel reported) throws ResistanceSocialNetworkException {
        assert_not_self_report(reporting, reported);
        assert_target_not_already_reported_by_source(reporting, reported);
    }

    private void assert_not_self_report(Rebel reportingRebel, Rebel reportedRebel) throws ResistanceSocialNetworkException {
        if (reportingRebel.getId().equals(reportedRebel.getId()))
            throw new ResistanceSocialNetworkException("can not self report");
    }

    private void assert_target_not_already_reported_by_source(Rebel reporting, Rebel reported) throws ResistanceSocialNetworkException {
        if (reporting.getReportedRebels().contains(reported.getId()))
            throw new ResistanceSocialNetworkException("rebel already reported");
    }
}
