package com.ray.pominowner.settlement.service;

import com.ray.pominowner.orders.repository.OrderRepository;
import com.ray.pominowner.settlement.domain.Settlement;
import com.ray.pominowner.settlement.repository.SettlementRepository;
import com.ray.pominowner.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.ray.pominowner.global.util.ExceptionMessage.NO_ORDER;
import static com.ray.pominowner.global.util.ExceptionMessage.NO_SETTLEMENT;
import static com.ray.pominowner.global.util.ExceptionMessage.NO_STORE;

@Service
@Transactional
@RequiredArgsConstructor
public class SettlementService {

    private final SettlementRepository settlementRepository;

    private final OrderRepository orderRepository;

    private final StoreRepository storeRepository;

    public Settlement create(Settlement settlement) {
        return settlementRepository.save(settlement);
    }

    public Settlement getSettlementByOrder(Long orderId) {
        orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException(NO_ORDER.getMessage()))
                .validateStatus();

        return settlementRepository.findSettlementByOrderIdAndDeleted(orderId, false)
                .orElseThrow(() -> new IllegalArgumentException(NO_SETTLEMENT.getMessage()));
    }

    public List<Settlement> getDailySettlementByStore(Long storeId, DateType dateType, LocalDate startDate, LocalDate endDate) {
        storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException(NO_STORE.getMessage()));

        return dateType.getSettlements(settlementRepository, storeId, startDate, endDate);
    }

}
