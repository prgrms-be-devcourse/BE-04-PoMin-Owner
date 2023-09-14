package com.ray.pominowner.store.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record PhoneNumberRequest(@NotBlank String phoneNumber) {
}
