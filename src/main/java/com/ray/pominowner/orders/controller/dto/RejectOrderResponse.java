package com.ray.pominowner.orders.controller.dto;

import com.ray.pominowner.orders.domain.Order;

public record RejectOrderResponse(
        Long orderId,
        String rejectReason
) {

    public RejectOrderResponse(Order rejected) {
        this(rejected.getId(),
                rejected.getRejectReason());
    }

}
