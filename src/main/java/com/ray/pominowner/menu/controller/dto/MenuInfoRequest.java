package com.ray.pominowner.menu.controller.dto;

import com.ray.pominowner.menu.domain.Menu;
import com.ray.pominowner.menu.domain.MenuImage;
import org.springframework.web.multipart.MultipartFile;

public record MenuInfoRequest(String name, MultipartFile image, String info, int price, Long storeId) {

  public Menu generateMenuEntityWithImage(MenuImage image) {

    return Menu.builder()
            .name(name)
            .info(info)
            .price(price)
            .menuImageId(image.getId())
            .storeId(storeId)
            .build();
  }

}
