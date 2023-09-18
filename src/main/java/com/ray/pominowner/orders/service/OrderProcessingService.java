package com.ray.pominowner.orders.service;

import com.ray.pominowner.orders.controller.dto.ReceiveOrderRequest;
import com.ray.pominowner.orders.domain.Order;
import com.ray.pominowner.payment.domain.Payment;
import com.ray.pominowner.payment.service.PaymentService;
import com.ray.pominowner.settlement.domain.Settlement;
import com.ray.pominowner.settlement.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderProcessingService {

    private final OrderService orderService;

    private final PaymentService paymentService;

    private final SettlementService settlementService;

    public void create(ReceiveOrderRequest request) {
        Order order = request.toEntity();
        Payment payment = request.toPaymentEntity();
        Settlement settlement = request.toSettlementEntity();

        orderService.receiveOrder(order);
        paymentService.create(payment);
        settlementService.create(settlement);
    }

}
