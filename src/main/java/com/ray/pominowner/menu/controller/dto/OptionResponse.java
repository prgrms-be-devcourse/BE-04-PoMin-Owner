package com.ray.pominowner.menu.controller.dto;

import com.ray.pominowner.menu.domain.Option;

import java.util.List;

public record OptionResponse(String name, int price, boolean selected) {

    public static List<OptionResponse> from(List<Option> options) {
        return options.stream()
                .map(option -> new OptionResponse(
                        option.getName(),
                        option.getPrice(),
                        option.isSelected()
                ))
                .toList();
    }

    public static OptionResponse from(Option option) {
        return new OptionResponse(
                option.getName(),
                option.getPrice(),
                option.isSelected()
        );
    }

}
