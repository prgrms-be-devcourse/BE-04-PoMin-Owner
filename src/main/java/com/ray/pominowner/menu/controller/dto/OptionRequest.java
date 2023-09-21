package com.ray.pominowner.menu.controller.dto;

import com.ray.pominowner.menu.domain.Option;

public record OptionRequest(String name, int price, boolean selected, Long optionGroupId) {

    public Option toEntity() {
        return Option.builder()
                .name(name)
                .price(price)
                .selected(selected)
                .build();
    }

}
