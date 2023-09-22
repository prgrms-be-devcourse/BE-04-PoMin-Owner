package com.ray.pominowner.store.service.vo;

import com.ray.pominowner.store.domain.Store;
import com.ray.pominowner.store.domain.StoreImage;

import java.util.List;

public record StoreInfo(Long id,
                        String name,
                        String mainAddress,
                        String detailAddress,
                        String phoneNumber,
                        List<String> images,
                        String openTime,
                        String closeTime) {


    public static StoreInfo from(Store store) {
        return new StoreInfo(
                store.getId(),
                store.getName(),
                store.getMainAddress(),
                store.getDetailAddress(),
                store.getTel(),
                store.getImages(),
                "12:00:00",
                "22:00:00"
        );
    }

}

