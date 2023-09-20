package com.ray.pominowner.settlement.controller;

import com.ray.pominowner.settlement.controller.dto.DailySettlementResponse;
import com.ray.pominowner.settlement.domain.ServiceType;
import com.ray.pominowner.settlement.domain.Settlement;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

@Component
public class DailySettlementResponseConverter {

    public List<DailySettlementResponse> convert(List<Settlement> settlements) {
        Map<LocalDate, Map<ServiceType, DailySettlementResponse>> groupedData = groupSettlementsByDateAndServiceType(settlements);

        List<DailySettlementResponse> responses = new ArrayList<>();
        for (Map<ServiceType, DailySettlementResponse> serviceTypeMap : groupedData.values()) {
            responses.addAll(serviceTypeMap.values());
        }

        return responses;
    }

    private Map<LocalDate, Map<ServiceType, DailySettlementResponse>> groupSettlementsByDateAndServiceType(List<Settlement> settlements) {
        return settlements.stream()
                .collect(Collectors.groupingBy(
                        settlement -> settlement.getPayOut().getPayOutDate(),
                        Collectors.groupingBy(
                                Settlement::getServiceType,
                                Collectors.collectingAndThen(
                                        Collectors.toList(),
                                        this::calculateTotalAmountAndFee
                                )
                        )
                ));
    }

    private DailySettlementResponse calculateTotalAmountAndFee(List<Settlement> settlements) {
        int totalSales = calculateTotal(settlements, settlement -> settlement.getSales().getSalesAmount());
        int totalPaymentFee = calculateTotal(settlements, settlement -> settlement.getFee().getPaymentFee());
        int totalBrokerageFee = calculateTotal(settlements, settlement -> settlement.getFee().getBrokerageFee());
        int totalValueAddedFee = calculateTotal(settlements, settlement -> settlement.getFee().getValueAddedFee());
        int totalPayout = calculateTotal(settlements, settlement -> settlement.getPayOut().getPayOutAmount());

        return new DailySettlementResponse(
                settlements.get(0).getPayOut().getPayOutDate(),
                settlements.get(0).getDepositStatus(),
                settlements.get(0).getSales().getSalesDate(),
                settlements.get(0).getServiceType(),
                totalSales,
                totalPaymentFee,
                totalBrokerageFee,
                totalValueAddedFee,
                totalPayout
        );
    }

    private int calculateTotal(List<Settlement> settlements, ToIntFunction<Settlement> mapper) {
        return settlements.stream()
                .mapToInt(mapper)
                .sum();
    }

}
