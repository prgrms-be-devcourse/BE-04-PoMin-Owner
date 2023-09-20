package com.ray.pominowner.settlement.controller.vo;

import com.ray.pominowner.settlement.service.DateType;

import java.time.LocalDate;

public record SettlementByStoreRequest(Long storeId, String dateType, LocalDate startDate, LocalDate endDate) {

}
