package com.ray.pominowner.settlement.service.vo;

import java.time.LocalDate;

public record SettlementByStoreRequest(Long storeId, String dateType, LocalDate startDate, LocalDate endDate) {

}
