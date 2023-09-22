package com.ray.pominowner.menu.controller.dto;

import com.ray.pominowner.menu.domain.Option;
import com.ray.pominowner.menu.domain.OptionGroup;

public record OptionRequest(String name, int price, boolean selected, Long optionGroupId) {

    public Option toEntity(OptionGroup optionGroup) {
        return Option.builder()
                .name(name)
                .price(price)
                .selected(selected)
                .optionGroup(optionGroup)
                .build();
    }

}
