package com.ray.pominowner.menu.service.vo;

import com.ray.pominowner.menu.domain.Menu;

public record MenuInfo(Long id,
                       String name,
                       int price,
                       String imageUrl,
                       String menuOptionName,
                       String description,
                       boolean verifyAge,
                       Long storeId) {

    public static MenuInfo from(Menu menu) {
        return new MenuInfo(
                menu.getId(),
                menu.getName(),
                menu.getPrice(),
                menu.getImage().getPath(),
                "best_seller", //TODO
                menu.getInfo(),
                false,
                menu.getStoreId()
        );
    }

}

