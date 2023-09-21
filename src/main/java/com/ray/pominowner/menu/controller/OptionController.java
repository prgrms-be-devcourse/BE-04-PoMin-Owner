package com.ray.pominowner.menu.controller;

import com.ray.pominowner.menu.controller.dto.OptionRequest;
import com.ray.pominowner.menu.controller.dto.OptionUpdateRequest;
import com.ray.pominowner.menu.domain.Option;
import com.ray.pominowner.menu.domain.OptionGroup;
import com.ray.pominowner.menu.service.OptionGroupService;
import com.ray.pominowner.menu.service.OptionService;
import com.ray.pominowner.menu.service.vo.OptionUpdateInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static com.ray.pominowner.menu.controller.dto.OptionGroupWithOptionResponse.OptionResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/options")
public class OptionController {

    private final OptionService optionService;
    private final OptionGroupService optionGroupService;

    @GetMapping
    public OptionResponse getOption(@RequestParam Long optionId) {
        Option option = optionService.getOption(optionId);

        return OptionResponse.from(option);
    }

    @PostMapping
    public ResponseEntity<Void> registerOption(@RequestBody OptionRequest request) {
        OptionGroup optionGroup = optionGroupService.getOptionGroup(request.optionGroupId());
        Long optionId = optionService.registerOption(request.toEntity(optionGroup), optionGroup);

        return ResponseEntity.created(URI.create("/api/v1/options/" + optionId)).build();
    }

    @PutMapping
    public void updateOption(@RequestBody OptionUpdateRequest request) {
        OptionUpdateInfo optionUpdateInfo = new OptionUpdateInfo(request.name(), request.price(), request.selected());
        optionService.updateOption(optionUpdateInfo, request.optionId());
    }

    @DeleteMapping
    public void deleteOption(@RequestParam Long optionId) {
        optionService.deleteOption(optionId);
    }

}
