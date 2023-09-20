package com.ray.pominowner.settlement.controller.vo;

import com.ray.pominowner.settlement.service.DateType;

import java.time.LocalDate;

public record SettlementByStoreRequest(Long storeId, DateType dateType, LocalDate startDate, LocalDate endDate) {

}
