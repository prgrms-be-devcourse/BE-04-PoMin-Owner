package com.ray.pominowner.menu.service;

import com.ray.pominowner.menu.domain.Menu;
import com.ray.pominowner.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public Long registerMenu(Menu menu) {
        return menuRepository.save(menu).getId();
    }

    @Transactional
    public void updateMenu(Menu menu, Long menuId) {
        Menu findMenu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 존재하지 않습니다."));
        Menu updatedMenu = findMenu.updateMenu(menu);
        menuRepository.save(updatedMenu);
    }

}
