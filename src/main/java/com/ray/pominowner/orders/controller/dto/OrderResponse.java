package com.ray.pominowner.orders.controller.dto;

import com.ray.pominowner.global.domain.PhoneNumber;
import com.ray.pominowner.orders.domain.Order;
import com.ray.pominowner.orders.domain.OrderStatus;

import java.time.LocalDateTime;

public record OrderResponse(
        String orderNumber,
        Integer receiptNumber,
        OrderStatus status,
        String requestedDetails,
        Integer totalPrice,
        LocalDateTime createdAt,
        PhoneNumber customerPhoneNumber,
        LocalDateTime reservationTime
) {

    public static OrderResponse of(Order order) {
        return new OrderResponse(
                order.getOrderNumber(),
                order.getReceiptNumber(),
                order.getStatus(),
                order.getRequestedDetails(),
                order.getTotalPrice(),
                order.getCreatedAt(),
                order.getCustomerPhoneNumber(),
                order.getReservationTime()
        );
    }

}
