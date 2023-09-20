package com.ray.pominowner.global.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {

    INVALID_AMOUNT("금액은 0이상의 값이어야 합니다."),
    NULL_PAYMENT_STATUS("결제 상태는 필수 값입니다."),
    NULL_PAYMENT_PROVIDER("PG사는 필수 값입니다."),
    INVALID_PAYOUT_DATE("지급일은 매출일과 같거나 그보다 늦어야 합니다."),
    NULL_FEE_OBJECT("수수료 관련 값은 필수입니다."),
    NULL_PAYOUT_OBJECT("지급 관련 값은 필수입니다."),
    NULL_SALES_OBJECT("매출 관련 값은 필수입니다."),
    NULL_DEPOSIT_STATUS("입금 상태는 필수 값입니다."),
    NULL_SERVICE_TYPE("서비스 타입은 필수 값입니다."),
    SALES_DATE_IS_AFTER_NOW("매출 일은 생성 날짜와 같거나 그보다 빨라야 합니다."),
    PAYOUT_DATE_IS_BEFORE_NOW("지급 일은 생성 날짜와 같거나 그보다 늦어야 합니다."),
    NO_PAYMENT("해당 결제 건이 존재하지 않습니다"),
    NO_ORDER("해당 주문 건이 존재하지 않습니다."),
    NO_SETTLEMENT("해당 결제 건이 존재하지 않습니다."),
    NO_STORE("해당 가게가 존재하지 않습니다."),
    INVALID_ORDER("취소되거나 거절된 주문입니다."),
    INVALID_DATE_TYPE("유요하지 않은 기준 날짜 타입입니다.");

    private final String message;

}
