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

    private int brokerageFee;

    private int valueAddedFee;

    public Fee(int paymentFee, int brokerageFee, int valueAddedFee) {
        validate(paymentFee < 0, INVALID_AMOUNT);
        validate(brokerageFee < 0, INVALID_AMOUNT);
        validate(valueAddedFee < 0, INVALID_AMOUNT);

        this.paymentFee = paymentFee;
        this.brokerageFee = brokerageFee;
        this.valueAddedFee = valueAddedFee;
    }

}
