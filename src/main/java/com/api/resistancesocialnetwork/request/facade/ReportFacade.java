package com.api.resistancesocialnetwork.request.facade;

import java.util.Optional;

public record ReportFacade(Integer sourceId, Integer targetId) {

    @Override
    public Integer sourceId() {
        return Optional.ofNullable(sourceId).orElse(0);
    }

    @Override
    public Integer targetId() {
        return Optional.ofNullable(targetId).orElse(0);
    }
}
