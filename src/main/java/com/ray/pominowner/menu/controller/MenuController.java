package com.ray.pominowner.menu.controller;

import com.ray.pominowner.menu.controller.dto.MenuInfoRequest;
import com.ray.pominowner.menu.domain.MenuImage;
import com.ray.pominowner.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/menu/")
public class MenuController {

    private final MenuService menuService;

    @PostMapping({"{storeId}"})
    public ResponseEntity<?> registerMenu(@RequestBody MenuInfoRequest request, @PathVariable Long storeId) {
        MenuImage image = menuService.createImage(request.image());
        Long menuId = menuService.registerMenu(request.generateMenuEntityWithImage(image));

        return ResponseEntity.created(URI.create("/api/v1/menu/" + storeId + "/" + menuId)).build();
    }

}
