package com.ray.pominowner.settlement.controller;

import com.ray.pominowner.settlement.controller.dto.SettlementResponse;
import com.ray.pominowner.settlement.service.DateType;
import com.ray.pominowner.settlement.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/settlements")
public class SettlementController {

    private final SettlementService settlementService;

    @GetMapping("/by-order/{orderId}")
    public SettlementResponse getSettlementByOrder(@PathVariable Long orderId) {
        return new SettlementResponse(settlementService.getSettlementByOrder(orderId));
    }

    @GetMapping("/by-store/{storeId}")
    public List<SettlementResponse> getDailySettlementByStore(@PathVariable Long storeId, @RequestParam DateType dateType, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return settlementService.getDailySettlementByStore(storeId, dateType, startDate, endDate)
                .stream()
                .map(SettlementResponse::new)
                .toList();
    }

}
