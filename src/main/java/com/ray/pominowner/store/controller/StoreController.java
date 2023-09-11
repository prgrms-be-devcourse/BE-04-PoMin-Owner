package com.ray.pominowner.store.controller;

import com.ray.pominowner.store.controller.dto.StoreRegisterRequest;
import com.ray.pominowner.store.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> registerStore(@RequestBody @Valid StoreRegisterRequest request) {
        Long storeId = storeService.registerStore(request.toEntity());
        return ResponseEntity.created(URI.create("/api/v1/stores/" + storeId)).build();
    }

}
