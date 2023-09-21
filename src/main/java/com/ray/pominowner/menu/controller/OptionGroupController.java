package com.ray.pominowner.menu.controller;


import com.ray.pominowner.menu.controller.dto.OptionGroupRequest;
import com.ray.pominowner.menu.service.OptionGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/option-groups")
public class OptionGroupController {

    private final OptionGroupService optionGroupService;

    @PostMapping
    public void registerOptionGroup(@RequestBody OptionGroupRequest request) {
        optionGroupService.registerOptionGroup(request.toEntity());
    }

}
