package com.ray.pominowner.menu.service;

import com.ray.pominowner.menu.domain.Option;
import com.ray.pominowner.menu.domain.OptionGroup;
import com.ray.pominowner.menu.repository.OptionGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptionService {

    private final OptionGroupRepository optionGroupRepository;

    public void registerOption(Option option, Long optionGroupId) {
        OptionGroup optionGroup = optionGroupRepository.findById(optionGroupId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 옵션그룹입니다."));
        optionGroup.addOption(option);
        optionGroupRepository.save(optionGroup);
    }

}
