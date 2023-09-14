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
        PaymentCreateRequest paymentRequest
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
    }

    public Payment toPaymentEntity() {
        return Payment.builder()
                .id(this.paymentRequest.id())
                .amount(this.paymentRequest.amount())
                .status(this.paymentRequest.status())
                .provider(this.paymentRequest.provider())
                .build();
    }

}
