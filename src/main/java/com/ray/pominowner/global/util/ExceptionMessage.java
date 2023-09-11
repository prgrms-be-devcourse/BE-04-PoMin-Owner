package com.ray.pominowner.global.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {

    INVALID_AMOUNT("금액은 0이상의 값이어야 합니다."),
    NULL_PAYMENT_STATUS("결제 상태는 필수 값입니다."),
    NULL_PAYMENT_PROVIDER("PG사는 필수 값입니다."),
    INVALID_PAYOUT_DATE("지급일은 매출일과같거나 그보다 늦어야 합니다."),
    NULL_FEE_OBJECT("수수료 관련 값은 필수입니다."),
    NULL_PAYOUT_OBJECT("지급 관련 값은 필수입니다."),
    NULL_SALES_OBJECT("매출 관련 값은 필수입니다.");

    private final String message;
}
