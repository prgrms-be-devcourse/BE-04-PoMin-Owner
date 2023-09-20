package com.ray.pominowner.settlement.controller.dto;

import com.ray.pominowner.settlement.domain.DepositStatus;
import com.ray.pominowner.settlement.domain.ServiceType;

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

}
