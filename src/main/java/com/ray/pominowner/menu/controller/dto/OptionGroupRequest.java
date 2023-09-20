package com.ray.pominowner.menu.controller.dto;

import com.ray.pominowner.menu.domain.OptionGroup;

public record OptionGroupRequest(String name, int maxOptionCount, boolean required, Long storeId) {

    public OptionGroup toEntity() {
        return OptionGroup.builder()
                .name(name)
                .maxOptionCount(maxOptionCount)
                .required(required)
                .storeId(storeId)
                .build();
    }

}
