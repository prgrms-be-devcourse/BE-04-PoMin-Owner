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
        Long storeId,
        PaymentCreateRequest payment
) {

    public static Order getEntity(ReceiveOrderRequest request) {
        return Order.builder()
                .id(request.id())
                .orderNumber(request.orderNumber())
                .status(OrderStatus.CONFIRMING)
                .requestedDetails(request.requestedDetails())
                .totalPrice(request.totalPrice())
                .customerPhoneNumber(new PhoneNumber(request.customerPhoneNumber()))
                .reservationTime(request.reservationTime())
                .storeId(request.storeId())
                .paymentId(request.payment.id())
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
