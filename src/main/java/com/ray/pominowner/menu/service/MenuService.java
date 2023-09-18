package com.ray.pominowner.menu.service;

import com.ray.pominowner.menu.domain.Menu;
import com.ray.pominowner.menu.domain.MenuImage;
import com.ray.pominowner.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public Long registerMenu(Menu menu) {
        // 구현 예정
        return menuRepository.save(menu).getId();
    }

    public MenuImage createImage(MultipartFile image) {
        // 구현 예정
        return null;
    }

}
