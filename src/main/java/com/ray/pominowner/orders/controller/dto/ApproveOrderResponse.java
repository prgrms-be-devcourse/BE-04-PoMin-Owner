package com.ray.pominowner.orders.controller.dto;

import com.ray.pominowner.orders.domain.Order;

import java.time.LocalTime;

public record ApproveOrderResponse(
        Long orderId,
        LocalTime estimatedCookingTime,
        Integer receiptNumber
) {

    public ApproveOrderResponse(Order approved) {
        this(approved.getId(),
                approved.getEstimatedCookingTime(),
                approved.getReceiptNumber());
    }

}
