package com.ray.pominowner.menu.service;

import com.ray.pominowner.menu.domain.OptionGroup;
import com.ray.pominowner.menu.repository.OptionGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptionGroupService {

    private final OptionGroupRepository optionGroupRepository;

    public void registerOptionGroup(OptionGroup optiongroup) {
        optionGroupRepository.save(optiongroup);
    }

}
