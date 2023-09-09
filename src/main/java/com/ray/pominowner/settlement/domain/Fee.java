package com.ray.pominowner.settlement.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static com.ray.pominowner.global.util.ExceptionMessage.INVALID_AMOUNT;
import static com.ray.pominowner.global.util.Validator.validate;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Fee {

    private int paymentFee;

    private int brokerage;

    private int vat;

    public Fee(int paymentFee, int brokerage, int vat) {
        validate(paymentFee < 0, INVALID_AMOUNT);
        validate(brokerage < 0, INVALID_AMOUNT);
        validate(vat < 0, INVALID_AMOUNT);

        this.paymentFee = paymentFee;
        this.brokerage = brokerage;
        this.vat = vat;
    }
}
