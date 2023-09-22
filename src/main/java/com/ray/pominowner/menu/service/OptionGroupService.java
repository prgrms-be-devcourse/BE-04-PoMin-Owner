package com.ray.pominowner.menu.service;

import com.ray.pominowner.menu.domain.OptionGroup;
import com.ray.pominowner.menu.repository.OptionGroupRepository;
import com.ray.pominowner.menu.service.vo.OptionGroupUpdateInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OptionGroupService {

    private final OptionGroupRepository optionGroupRepository;

    public OptionGroup getOptionGroup(Long optionGroupId) {
        return optionGroupRepository.findById(optionGroupId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 옵션그룹입니다."));
    }

    public Long registerOptionGroup(OptionGroup optiongroup) {
        return optionGroupRepository.save(optiongroup).getId();
    }

    public void updateOptionGroup(OptionGroupUpdateInfo optionGroupUpdateInfo) {
        OptionGroup optionGroupToUpdate = getOptionGroup(optionGroupUpdateInfo.optionGroupId());
        OptionGroup updatedOptionGroup = optionGroupToUpdate.update(optionGroupUpdateInfo);
        optionGroupRepository.save(updatedOptionGroup);
    }

    public void deleteOptionGroup(Long optionGroupId) {
        OptionGroup optionGroup = getOptionGroup(optionGroupId);
        optionGroupRepository.delete(optionGroup);
    }

}
