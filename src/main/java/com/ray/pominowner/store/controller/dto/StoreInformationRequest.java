package com.ray.pominowner.store.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record StoreInformationRequest(@NotBlank String information) {
}
