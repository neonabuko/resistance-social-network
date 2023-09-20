package com.api.resistancesocialnetwork.request;

import com.api.resistancesocialnetwork.model.Item;

public record RequestTrade(Integer sourceInventoryId, Item sourceTradeItem, Integer targetInventoryId, Item targetTradeItem) {
}
