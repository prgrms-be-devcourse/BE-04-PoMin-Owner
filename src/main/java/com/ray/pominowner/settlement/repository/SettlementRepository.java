package com.ray.pominowner.settlement.repository;

import com.ray.pominowner.settlement.domain.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {

    Optional<Settlement> findSettlementByOrderIdAndDeleted(Long orderId, boolean deleted);

}
