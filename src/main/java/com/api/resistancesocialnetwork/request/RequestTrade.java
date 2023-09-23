package com.api.resistancesocialnetwork.request;

public record RequestTrade(
        Integer sourceInventoryId,
        String sourceTradeItemName,
        Integer targetInventoryId,
        String targetTradeItemName
) {
}
