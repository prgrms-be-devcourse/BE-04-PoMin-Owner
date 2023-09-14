package com.ray.pominowner.store.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ray.pominowner.store.controller.dto.CategoryRequest;
import com.ray.pominowner.store.controller.dto.PhoneNumberRequest;
import com.ray.pominowner.store.controller.dto.StoreRegisterRequest;
import com.ray.pominowner.store.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    public ResponseEntity<Void> registerStore(@RequestBody @Valid StoreRegisterRequest request) throws JsonProcessingException {
        Long storeId = storeService.registerStore(request.toEntity());
        return ResponseEntity.created(URI.create("/api/v1/stores/" + storeId)).build();
    }

    @PostMapping("/{id}/categories")
    public void registerCategory(@RequestBody @Valid CategoryRequest categories, @PathVariable(value = "id") Long storeId) {
        storeService.registerCategory(categories.categories(), storeId);
    }

    @PostMapping("/{id}/phone-numbers")
    public void registerPhoneNumber(@RequestBody @Valid PhoneNumberRequest phoneNumberRequest, @PathVariable(value = "id") Long storeId) {
        storeService.registerPhoneNumber(phoneNumberRequest.phoneNumber(), storeId);
    }

}
