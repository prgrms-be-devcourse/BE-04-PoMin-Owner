package com.ray.pominowner.settlement.controller;

import com.ray.pominowner.settlement.controller.dto.DailySettlementResponse;
import com.ray.pominowner.settlement.controller.dto.DailySettlementResponseConverter;
import com.ray.pominowner.settlement.controller.dto.SettlementResponse;
import com.ray.pominowner.settlement.controller.vo.SettlementByStoreRequest;
import com.ray.pominowner.settlement.domain.Settlement;
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

    private final DailySettlementResponseConverter dailySettlementResponseConverter;

    @GetMapping("/by-order/{orderId}")
    public SettlementResponse getSettlementByOrder(@PathVariable Long orderId) {
        return new SettlementResponse(settlementService.getSettlementByOrder(orderId));
    }

    @GetMapping("/by-store/{storeId}")
    public List<DailySettlementResponse> getDailySettlementByStore(@PathVariable Long storeId, @RequestParam DateType dateType, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        List<Settlement> settlements = settlementService.getDailySettlementByStore(new SettlementByStoreRequest(storeId, dateType, startDate, endDate));

        return dailySettlementResponseConverter.convert(settlements);
    }

}
