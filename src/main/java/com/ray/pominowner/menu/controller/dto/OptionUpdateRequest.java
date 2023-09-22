package com.ray.pominowner.menu.controller.dto;

public record OptionUpdateRequest(String name, int price, boolean selected, Long optionId) {
}
