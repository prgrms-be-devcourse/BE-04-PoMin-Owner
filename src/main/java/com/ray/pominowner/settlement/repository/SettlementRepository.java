package com.ray.pominowner.settlement.repository;

import com.ray.pominowner.settlement.domain.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {

    Optional<Settlement> findSettlementByOrderIdAndDeleted(Long orderId, boolean deleted);

    List<Settlement> findSettlementsByStoreIdAndDeletedAndSales_SalesDateBetween(Long storeId, boolean deleted, LocalDate startDate, LocalDate endDate);
    
    List<Settlement> findSettlementsByStoreIdAndDeletedAndPayOut_PayOutDateBetween(Long storeId, boolean deleted, LocalDate startDate, LocalDate endDate);

}
