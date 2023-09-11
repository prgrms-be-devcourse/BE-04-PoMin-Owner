package com.ray.pominowner.store.controller.dto;

import com.ray.pominowner.store.domain.RequiredStoreInfo;
import com.ray.pominowner.store.domain.Store;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StoreRegisterRequest(@NotNull Long businessNumber,
                                   @NotBlank String name,
                                   @NotBlank String address,
                                   @NotBlank String logoImage) {

    public Store toEntity() {
        RequiredStoreInfo requiredStoreInfo = new RequiredStoreInfo(businessNumber, name, address, logoImage);
        return new Store(requiredStoreInfo);
    }

}
