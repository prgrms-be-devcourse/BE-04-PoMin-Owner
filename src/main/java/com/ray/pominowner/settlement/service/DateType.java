package com.ray.pominowner.settlement.service;

import com.ray.pominowner.settlement.service.vo.SettlementByStoreRequest;
import com.ray.pominowner.settlement.domain.Settlement;
import com.ray.pominowner.settlement.repository.SettlementRepository;

import java.util.List;

public enum DateType {
    SOLD {
        @Override
        public List<Settlement> getSettlements(SettlementRepository settlementRepository, SettlementByStoreRequest request) {
            return settlementRepository.findSettlementsByStoreIdAndDeletedAndSales_SalesDateBetween(request.storeId(), false, request.startDate(), request.endDate());
        }
    },
    PAYOUT {
        @Override
        public List<Settlement> getSettlements(SettlementRepository settlementRepository, SettlementByStoreRequest request) {
            return settlementRepository.findSettlementsByStoreIdAndDeletedAndPayOut_PayOutDateBetween(request.storeId(), false, request.startDate(), request.endDate());
        }
    };

    public abstract List<Settlement> getSettlements(SettlementRepository settlementRepository, SettlementByStoreRequest request);
}
