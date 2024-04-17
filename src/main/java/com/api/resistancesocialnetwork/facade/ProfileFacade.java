package com.api.resistancesocialnetwork.facade;

import java.util.List;

public record ProfileFacade(
        String name,
        Integer age,
        String gender,
        Double latitude,
        Double longitude,
        String base,
        List<ItemFacade> inventory
) {}

