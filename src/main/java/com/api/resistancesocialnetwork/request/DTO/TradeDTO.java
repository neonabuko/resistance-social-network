package com.api.resistancesocialnetwork.request.DTO;

import java.util.Optional;

public record TradeDTO(Integer sourceInventoryId, Integer sourceTradeItemId, Integer targetInventoryId, Integer targetTradeItemId) {

    @Override
    public Integer sourceInventoryId() {
        return Optional.ofNullable(sourceInventoryId).orElse(0);
    }

    @Override
    public Integer sourceTradeItemId() {
        return Optional.ofNullable(sourceTradeItemId).orElse(0);
    }

    @Override
    public Integer targetInventoryId() {
        return Optional.ofNullable(targetInventoryId).orElse(0);
    }

    @Override
    public Integer targetTradeItemId() {
        return Optional.ofNullable(targetTradeItemId).orElse(0);
    }
}
