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

    private int payment;

    private int brokerage;

    private int valueAdded;

    public Fee(int payment, int brokerage, int valueAdded) {
        validate(payment < 0, INVALID_AMOUNT);
        validate(brokerage < 0, INVALID_AMOUNT);
        validate(valueAdded < 0, INVALID_AMOUNT);

        this.payment = payment;
        this.brokerage = brokerage;
        this.valueAdded = valueAdded;
    }
}
