package com.ray.pominowner.settlement.repository;

import com.ray.pominowner.settlement.domain.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {

}
