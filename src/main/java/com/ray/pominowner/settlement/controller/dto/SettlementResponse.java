package com.ray.pominowner.settlement.controller.dto;

import com.ray.pominowner.settlement.domain.Settlement;

import java.time.LocalDate;

public record SettlementResponse(
        Long id,
        int salesAmount,
        int paymentFee,
        int brokerageFee,
        int valueAddedFee,
        int payOutAmount,
        LocalDate payOutDate
) {


    public SettlementResponse(Settlement settlement) {
        this(
                settlement.getId(),
                settlement.getSales().getSalesAmount(),
                settlement.getFee().getPaymentFee(),
                settlement.getFee().getBrokerageFee(),
                settlement.getFee().getValueAddedFee(),
                settlement.getPayOut().getPayOutAmount(),
                settlement.getPayOut().getPayOutDate()
        );
    }

}
