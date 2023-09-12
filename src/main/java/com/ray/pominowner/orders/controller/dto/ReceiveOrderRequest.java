package com.ray.pominowner.orders.controller.dto;

import com.ray.pominowner.global.domain.PhoneNumber;
import com.ray.pominowner.orders.domain.Order;
import com.ray.pominowner.orders.domain.OrderStatus;

import java.time.LocalDateTime;

public record ReceiveOrderRequest(
        String orderNumber,
        String requestedDetails,
        Integer totalPrice,
        PhoneNumber customerPhoneNumber,
        LocalDateTime reservationTime,
        Long storeId
) {

    public static Order getEntity(Long id, ReceiveOrderRequest request) {
        return Order.builder()
                .id(id)
                .orderNumber(request.orderNumber())
                .status(OrderStatus.CONFIRMING)
                .requestedDetails(request.requestedDetails())
                .totalPrice(request.totalPrice())
                .customerPhoneNumber(request.customerPhoneNumber())
                .reservationTime(request.reservationTime())
                .storeId(request.storeId())
                .build();
    }

}
