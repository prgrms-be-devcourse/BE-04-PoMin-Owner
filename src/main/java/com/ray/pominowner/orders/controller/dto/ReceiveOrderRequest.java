package com.ray.pominowner.orders.controller.dto;

import com.ray.pominowner.global.domain.PhoneNumber;
import com.ray.pominowner.orders.domain.Order;
import com.ray.pominowner.orders.domain.OrderStatus;
import com.ray.pominowner.payment.domain.Payment;
import com.ray.pominowner.payment.dto.PaymentCreateRequest;
import com.ray.pominowner.settlement.domain.DepositStatus;
import com.ray.pominowner.settlement.domain.Fee;
import com.ray.pominowner.settlement.domain.PayOut;
import com.ray.pominowner.settlement.domain.Sales;
import com.ray.pominowner.settlement.domain.ServiceType;
import com.ray.pominowner.settlement.domain.Settlement;

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
                .id(payment.id())
                .amount(payment.amount())
                .status(payment.status())
                .provider(payment.provider())
                .build();
    }

    public Settlement toSettlementEntity() {
        Fee fee = new Fee(payment.provider(), payment.amount());

        return Settlement.builder()
                .id(payment.id())
                .fee(fee)
                .payOut(new PayOut(payment.amount(), fee, orderedAt.toLocalDate()))
                .sales(new Sales(payment.amount(), orderedAt.toLocalDate()))
                .depositStatus(DepositStatus.SCHEDULED)
                .serviceType(ServiceType.PACKAGING)
                .storeId(storeId)
                .orderId(id)
                .paymentId(payment.id())
                .deleted(false)
                .build();
    }

}
