package com.ray.pominowner.menu.controller;

import com.ray.pominowner.menu.controller.dto.OptionGroupRequest;
import com.ray.pominowner.menu.controller.dto.OptionGroupUpdateRequest;
import com.ray.pominowner.menu.controller.dto.OptionGroupWithOptionResponse;
import com.ray.pominowner.menu.domain.OptionGroup;
import com.ray.pominowner.menu.service.OptionGroupService;
import com.ray.pominowner.menu.service.vo.OptionGroupUpdateInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/option-groups")
public class OptionGroupController {

    private final OptionGroupService optionGroupService;

    @GetMapping("/{optionGroupId}")
    public OptionGroupWithOptionResponse getOptionGroup(@PathVariable Long optionGroupId) {
        OptionGroup optionGroup = optionGroupService.getOptionGroup(optionGroupId);

        return OptionGroupWithOptionResponse.from(optionGroup);
    }

    @PostMapping
    public ResponseEntity<Void> registerOptionGroup(@RequestBody OptionGroupRequest request) {
        Long optionGroupId = optionGroupService.registerOptionGroup(request.toEntity());

        return ResponseEntity.created(URI.create("/api/v1/option-groups/" + optionGroupId)).build();
    }

    @PutMapping("/{optionGroupId}")
    public void updateOptionGroup(@RequestBody OptionGroupUpdateRequest request, @PathVariable Long optionGroupId) {
        OptionGroupUpdateInfo optionGroupUpdateInfo = new OptionGroupUpdateInfo(
                request.name(),
                request.maxOptionCount(),
                request.required()
        );
        optionGroupService.updateOptionGroup(optionGroupUpdateInfo, optionGroupId);
    }

    @DeleteMapping("/{optionGroupId}")
    public void deleteOptionGroup(@PathVariable Long optionGroupId) {
        optionGroupService.deleteOptionGroup(optionGroupId);
    }

}
