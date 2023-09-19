package com.ray.pominowner.settlement.service;

import com.ray.pominowner.orders.repository.OrderRepository;
import com.ray.pominowner.settlement.controller.dto.SettlementResponse;
import com.ray.pominowner.settlement.domain.Settlement;
import com.ray.pominowner.settlement.repository.SettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException(NO_ORDER.getMessage()))
                .validateStatus();

        Settlement settlement = settlementRepository.findSettlementByOrderIdAndDeleted(orderId, false)
                .orElseThrow(() -> new IllegalArgumentException(NO_SETTLEMENT.getMessage()));

        return new SettlementResponse(settlement);
    }

}
