package com.ray.pominowner.menu.service;

import com.ray.pominowner.menu.domain.Option;
import com.ray.pominowner.menu.domain.OptionGroup;
import com.ray.pominowner.menu.repository.OptionGroupRepository;
import com.ray.pominowner.menu.repository.OptionRepository;
import com.ray.pominowner.menu.service.vo.OptionUpdateInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OptionService {

    private final OptionGroupRepository optionGroupRepository;
    private final OptionRepository optionRepository;

    public Option getOption(Long optionId) {
        return optionRepository.findById(optionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 옵션입니다."));
    }

    public Long registerOption(Option option, OptionGroup optionGroup) {
        Option optionGroupAddedOption = optionGroup.addOption(option);
        optionGroupRepository.save(optionGroup);

        return optionGroupAddedOption.getId();
    }

    public void updateOption(OptionUpdateInfo optionUpdateInfo ) {
        Option option = getOption(optionUpdateInfo.optionId());
        Option updatedOption = option.update(optionUpdateInfo);
        optionRepository.save(updatedOption);
    }

    public void deleteOption(Long optionId) {
        Option option = getOption(optionId);
        optionRepository.delete(option);
    }

}
