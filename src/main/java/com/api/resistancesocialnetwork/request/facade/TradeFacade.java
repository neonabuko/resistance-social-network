package com.api.resistancesocialnetwork.request.facade;

import java.util.Optional;

public record TradeFacade(Integer leftRebelId, Integer leftItemId, Integer rightRebelId, Integer rightItemId) {

    public Integer leftRebelId() {
        return Optional.ofNullable(leftRebelId).orElse(0);
    }
    public Integer leftItemId() {
        return Optional.ofNullable(leftItemId).orElse(0);
    }
    public Integer rightRebelId() {
        return Optional.ofNullable(rightRebelId).orElse(0);
    }
    public Integer rightItemId() {
        return Optional.ofNullable(rightItemId).orElse(0);
    }
}
