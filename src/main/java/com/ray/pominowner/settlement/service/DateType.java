package com.ray.pominowner.settlement.service;

import com.ray.pominowner.settlement.domain.Settlement;
import com.ray.pominowner.settlement.repository.SettlementRepository;

import java.time.LocalDate;
import java.util.List;

public enum DateType {
    SOLD {
        @Override
        public List<Settlement> getSettlements(SettlementRepository settlementRepository, Long storeId, LocalDate startDate, LocalDate endDate) {
            return settlementRepository.findSettlementsByStoreIdAndDeletedAndSales_SalesDateBetween(storeId, false, startDate, endDate);
        }
    },
    PAYOUT {
        @Override
        public List<Settlement> getSettlements(SettlementRepository settlementRepository, Long storeId, LocalDate startDate, LocalDate endDate) {
            return settlementRepository.findSettlementsByStoreIdAndDeletedAndPayOut_PayOutDateBetween(storeId, false, startDate, endDate);
        }
    };

    public abstract List<Settlement> getSettlements(SettlementRepository settlementRepository, Long storeId, LocalDate startDate, LocalDate endDate);
}
