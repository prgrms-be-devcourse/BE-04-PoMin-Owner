package com.ray.pominowner.payment.controller;

import com.ray.pominowner.payment.dto.PaymentCreateRequest;
import com.ray.pominowner.payment.service.PaymentService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/payment")
    public ResponseEntity<Void> create(@RequestBody PaymentCreateRequest request) {
        Long paymentId = paymentService.create(request.toEntity());

        return ResponseEntity.created(URI.create("/api/v1/payment/" + paymentId)).build();
    }

}
