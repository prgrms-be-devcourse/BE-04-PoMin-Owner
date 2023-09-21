package com.ray.pominowner.settlement.controller.dto;

import com.ray.pominowner.settlement.domain.DepositStatus;
import com.ray.pominowner.settlement.domain.ServiceType;
import com.ray.pominowner.settlement.domain.Settlement;

import java.time.LocalDate;

public record DailySettlementResponse(
        LocalDate payOutDate,
        DepositStatus depositStatus,
        LocalDate salesDate,
        ServiceType serviceType,
        int salesAmount,
        int paymentFee,
        int brokerageFee,
        int valueAddedFee,
        int payOutAmount
) {

    public DailySettlementResponse(Settlement settlement) {
        this(
                settlement.getPayOut().getPayOutDate(),
                settlement.getDepositStatus(),
                settlement.getSales().getSalesDate(),
                settlement.getServiceType(),
                settlement.getSales().getSalesAmount(),
                settlement.getFee().getPaymentFee(),
                settlement.getFee().getBrokerageFee(),
                settlement.getFee().getValueAddedFee(),
                settlement.getPayOut().getPayOutAmount()
        );
    }

}
