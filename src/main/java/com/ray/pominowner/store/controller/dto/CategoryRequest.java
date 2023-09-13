package com.ray.pominowner.store.controller.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CategoryRequest(
        @NotEmpty List<String> categories) {
}
