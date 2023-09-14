package com.ray.pominowner.payment.dto;

import com.ray.pominowner.payment.domain.PGType;
import com.ray.pominowner.payment.domain.PaymentStatus;

public record PaymentCreateRequest(Long id, int amount, PaymentStatus status, PGType provider) {
}
