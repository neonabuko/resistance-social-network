package com.api.resistancesocialnetwork.request.facade;

import java.util.Optional;

public record TradeFacade(Integer sourceRebelId, Integer sourceItemId, Integer targetRebelId, Integer targetItemId) {

    public Integer sourceRebelId() {
        return Optional.ofNullable(sourceRebelId).orElse(0);
    }

    public Integer sourceItemId() {
        return Optional.ofNullable(sourceItemId).orElse(0);
    }

    public Integer targetRebelId() {
        return Optional.ofNullable(targetRebelId).orElse(0);
    }

    public Integer targetItemId() {
        return Optional.ofNullable(targetItemId).orElse(0);
    }
}
