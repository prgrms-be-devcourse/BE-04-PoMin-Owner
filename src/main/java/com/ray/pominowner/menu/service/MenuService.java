package com.ray.pominowner.menu.service;

import com.ray.pominowner.menu.domain.Menu;
import com.ray.pominowner.menu.domain.MenuOptionGroup;
import com.ray.pominowner.menu.domain.OptionGroup;
import com.ray.pominowner.menu.repository.MenuRepository;
import com.ray.pominowner.global.vo.InfoSender;
import com.ray.pominowner.menu.repository.OptionGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final OptionGroupRepository optionGroupRepository;
    private final InfoSender infoSender;

    public Long registerMenu(Menu menu) {
        Long id = menuRepository.save(menu).getId();
        infoSender.send(menu);

        return id;
    }

    public void updateMenu(Menu menu, Long menuId) {
        Menu findMenu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 존재하지 않습니다."));
        Menu updatedMenu = findMenu.updateMenu(menu);
        menuRepository.save(updatedMenu);

        infoSender.send(updatedMenu);
    }

    public void addOptionGroupToMenu(Long menuId, Long optionGroupId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 존재하지 않습니다."));

        OptionGroup optionGroup = optionGroupRepository.findById(optionGroupId)
                .orElseThrow(() -> new IllegalArgumentException("해당 옵션 그룹이 존재하지 않습니다."));

        menu.addMenuOptionGroup(new MenuOptionGroup(menu, optionGroup));
        menuRepository.save(menu);

        infoSender.send(menu, optionGroup);
    }

}
