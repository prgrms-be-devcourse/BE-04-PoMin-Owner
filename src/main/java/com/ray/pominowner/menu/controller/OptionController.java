package com.ray.pominowner.menu.controller;

import com.ray.pominowner.menu.controller.dto.OptionRequest;
import com.ray.pominowner.menu.service.OptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/options")
public class OptionController {

    private final OptionService optionService;

    @PostMapping
    public void registerOption(@RequestBody OptionRequest request) {
        optionService.registerOption(request.toEntity(), request.optionGroupId());
    }


}
