package com.ray.pominowner.settlement.controller;

import com.ray.pominowner.settlement.controller.dto.SettlementResponse;
import com.ray.pominowner.settlement.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/settlements")
public class SettlementController {

    private final SettlementService settlementService;

    @GetMapping("/{orderId}")
    public SettlementResponse getSettlementInfoByOrder(@PathVariable Long orderId) {
        return settlementService.getSettlementByOrder(orderId);
    }

}
