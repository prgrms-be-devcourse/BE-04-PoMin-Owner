package com.ray.pominowner.menu.controller.dto;

import com.ray.pominowner.menu.domain.OptionGroup;

import java.util.List;

public record OptionGroupWithOptionResponse(
        OptionGroupResponse optionGroup,
        List<OptionResponse> options) {

    public static OptionGroupWithOptionResponse from(OptionGroup optionGroup) {
        return new OptionGroupWithOptionResponse(
                new OptionGroupResponse(
                        optionGroup.getName(),
                        optionGroup.getMaxOptionCount(),
                        optionGroup.isRequired()
                ),
                OptionResponse.from(optionGroup.getOptions())
        );
    }

    private record OptionGroupResponse(String name, int maxOptionCount, boolean required) {
    }

}


