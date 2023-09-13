package com.ray.pominowner.payment.dto;

import com.ray.pominowner.payment.domain.PGType;
import com.ray.pominowner.payment.domain.Payment;
import com.ray.pominowner.payment.domain.PaymentStatus;

public record PaymentCreateRequest(int amount, PaymentStatus status, PGType provider) {

    public Payment toEntity() {
        return new Payment(amount, status, provider);
    }

}
