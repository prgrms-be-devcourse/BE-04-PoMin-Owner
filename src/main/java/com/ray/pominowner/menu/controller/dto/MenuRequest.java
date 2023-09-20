package com.ray.pominowner.menu.controller.dto;

import com.ray.pominowner.menu.domain.Menu;
import com.ray.pominowner.menu.domain.MenuImage;

public record MenuRequest(String name, String info, int price, Long storeId) {

  public Menu toMenuEntity(MenuImage image) {
    return Menu.builder()
            .name(name)
            .info(info)
            .price(price)
            .image(image)
            .storeId(storeId)
            .build();
  }

}
