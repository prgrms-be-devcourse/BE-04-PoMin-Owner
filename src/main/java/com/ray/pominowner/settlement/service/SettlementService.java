package com.ray.pominowner.settlement.service;

import com.ray.pominowner.orders.domain.Order;
import com.ray.pominowner.orders.domain.OrderStatus;
import com.ray.pominowner.orders.repository.OrderRepository;
import com.ray.pominowner.settlement.controller.dto.SettlementResponse;
import com.ray.pominowner.settlement.domain.Settlement;
import com.ray.pominowner.settlement.repository.SettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ray.pominowner.global.util.ExceptionMessage.INVALID_ORDER;
import static com.ray.pominowner.global.util.ExceptionMessage.NO_ORDER;
import static com.ray.pominowner.global.util.ExceptionMessage.NO_SETTLEMENT;

@Service
@Transactional
@RequiredArgsConstructor
public class SettlementService {

    private final SettlementRepository settlementRepository;

    private final OrderRepository orderRepository;

    public Settlement create(Settlement settlement) {
        return settlementRepository.save(settlement);
    }

    public SettlementResponse getSettlementByOrder(Long orderId) {
        validateOrder(orderId);

        Settlement settlement = settlementRepository.findSettlementByOrderIdAndDeleted(orderId, false)
                .orElseThrow(() -> new IllegalArgumentException(NO_SETTLEMENT.getMessage()));

        return new SettlementResponse(settlement);
    }

    private void validateOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException(NO_ORDER.getMessage()));

        if (order.getStatus() == OrderStatus.CANCELED || order.getStatus() == OrderStatus.REJECTED) {
            throw new IllegalArgumentException(INVALID_ORDER.getMessage());
        }
    }

}
