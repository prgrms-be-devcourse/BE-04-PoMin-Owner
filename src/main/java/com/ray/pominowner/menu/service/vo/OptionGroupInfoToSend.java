package com.ray.pominowner.menu.service.vo;

import com.ray.pominowner.menu.domain.Menu;
import com.ray.pominowner.menu.domain.OptionGroup;

import java.util.List;

public record OptionGroupInfoToSend(
        Long groupId,
        String groupName,
        boolean defaultSelection,
        int maxSize,
        Long menuId,
        List<OptionInfoToSend> options) {

    private record OptionInfoToSend(
            Long optionId,
            String name,
            int price) {

    }

    public static OptionGroupInfoToSend from(OptionGroup optionGroup, Menu menu) {
        return new OptionGroupInfoToSend(
                optionGroup.getId(),
                optionGroup.getName(),
                optionGroup.isRequired(),
                optionGroup.getMaxOptionCount(),
                menu.getId(),
                optionGroup.getOptions().stream()
                        .map(optionInfo -> new OptionInfoToSend(
                                optionInfo.getId(),
                                optionInfo.getName(),
                                optionInfo.getPrice()))
                        .toList()
        );
    }

}
