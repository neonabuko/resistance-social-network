package com.api.resistancesocialnetwork.request.facade;

import java.util.Optional;

public record ReportFacade(Integer reportingId, Integer reportedId) {

    public Integer reportingId() {
        return Optional.ofNullable(reportingId).orElse(0);
    }

    public Integer reportedId() {
        return Optional.ofNullable(reportedId).orElse(0);
    }
}
