package com.ray.pominowner.orders.controller.dto;

import com.ray.pominowner.global.domain.PhoneNumber;
import com.ray.pominowner.orders.domain.Order;
import com.ray.pominowner.orders.domain.OrderStatus;
import com.ray.pominowner.payment.domain.Payment;
import com.ray.pominowner.payment.dto.PaymentCreateRequest;

import java.time.LocalDateTime;

public record ReceiveOrderRequest(
        Long id,
        String orderNumber,
        String requestedDetails,
        Integer totalPrice,
        String customerPhoneNumber,
        LocalDateTime reservationTime,
        LocalDateTime orderedAt,
        Long storeId,
        PaymentCreateRequest payment
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
                .paymentId(this.payment().id())
                .orderedAt(this.orderedAt())
                .build();
    }

    public Payment toPaymentEntity() {
        return Payment.builder()
                .id(this.payment.id())
                .amount(this.payment.amount())
                .status(this.payment.status())
                .provider(this.payment.provider())
                .build();
    }

}
