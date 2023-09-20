package com.ray.pominowner.orders.service.dto;

public record ApproveOrderNotifyRequest(
        int receiptNumber,
        String requestedDetails,
        int cookingMinute,
        long storeId
) {

}
