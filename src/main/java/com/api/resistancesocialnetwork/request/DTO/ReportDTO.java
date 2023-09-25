package com.api.resistancesocialnetwork.request.DTO;

import java.util.Optional;

public record ReportDTO(Integer sourceId, Integer targetId) {

    @Override
    public Integer sourceId() {
        return Optional.ofNullable(sourceId).orElse(0);
    }

    @Override
    public Integer targetId() {
        return Optional.ofNullable(targetId).orElse(0);
    }
}
