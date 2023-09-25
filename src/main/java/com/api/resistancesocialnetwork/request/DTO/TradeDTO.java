package com.api.resistancesocialnetwork.request.DTO;

import java.util.Optional;

public record TradeDTO(Integer sourceInventoryId, Integer sourceItemId, Integer targetInventoryId, Integer targetItemId) {

    @Override
    public Integer sourceInventoryId() {
        return Optional.ofNullable(sourceInventoryId).orElse(0);
    }

    public Integer sourceItemId() {
        return Optional.ofNullable(sourceItemId).orElse(0);
    }

    @Override
    public Integer targetInventoryId() {
        return Optional.ofNullable(targetInventoryId).orElse(0);
    }

    public Integer targetItemId() {
        return Optional.ofNullable(targetItemId).orElse(0);
    }
}
