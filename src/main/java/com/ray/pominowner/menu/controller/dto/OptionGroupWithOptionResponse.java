package com.ray.pominowner.menu.controller.dto;

import com.ray.pominowner.menu.domain.Option;
import com.ray.pominowner.menu.domain.OptionGroup;

import java.util.List;
import java.util.stream.Collectors;

public record OptionGroupWithOptionResponse(
        OptionGroupResponse optionGroupResponse,
        List<OptionResponse> optionResponses) {

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

    public record OptionResponse(String name, int price, boolean selected) {

        public static List<OptionResponse> from(List<Option> options) {
            return options.stream()
                    .map(option -> new OptionResponse(
                            option.getName(),
                            option.getPrice(),
                            option.isSelected()
                    ))
                    .collect(Collectors.toList());
        }

        public static OptionResponse from(Option option) {
            return new OptionResponse(
                    option.getName(),
                    option.getPrice(),
                    option.isSelected()
            );
        }

    }

}


