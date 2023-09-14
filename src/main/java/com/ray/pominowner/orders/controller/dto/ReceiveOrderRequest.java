package com.ray.pominowner.orders.controller.dto;

import com.ray.pominowner.global.domain.PhoneNumber;
import com.ray.pominowner.orders.domain.Order;
import com.ray.pominowner.orders.domain.OrderStatus;

import java.time.LocalDateTime;

public record ReceiveOrderRequest(
        Long id,
        String orderNumber,
        String requestedDetails,
        Integer totalPrice,
        String customerPhoneNumber,
        LocalDateTime reservationTime,
        Long storeId,
        Long paymentId
) {

    public Order toEntity() {
        return Order.builder()
                .id(this.id())
                .orderNumber(this.orderNumber())
                .status(OrderStatus.CONFIRMING)
                .requestedDetails(this.requestedDetails())
                .totalPrice(this.totalPrice())
                .customerPhoneNumber(new PhoneNumber(this.customerPhoneNumber()))
                .reservationTime(this.reservationTime())
                .storeId(this.storeId())
                .paymentId(this.paymentId())
                .build();
    }

}
