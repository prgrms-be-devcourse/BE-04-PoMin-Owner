package com.ray.pominowner.settlement.domain;

import com.ray.pominowner.payment.domain.PGType;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import static com.ray.pominowner.global.util.ExceptionMessage.INVALID_AMOUNT;
import static com.ray.pominowner.global.util.ExceptionMessage.NULL_PAYMENT_PROVIDER;
import static com.ray.pominowner.global.util.Validator.validate;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Fee {

    private static final double BROKERAGE_FEE = 0.06;

    private static final double VALUE_ADDED_FEE = 0.01;

    private int paymentFee;

    private int brokerageFee;

    private int valueAddedFee;

    public Fee(PGType pgType, int paymentAmount) {
        Assert.notNull(pgType, NULL_PAYMENT_PROVIDER.getMessage());
        validate(paymentAmount >= 0, INVALID_AMOUNT);

        this.paymentFee = pgType.calculate(paymentAmount);
        this.brokerageFee = (int) (paymentAmount * BROKERAGE_FEE);
        this.valueAddedFee = (int) (paymentAmount * VALUE_ADDED_FEE);
    }

    public int calculateTotalMinusAmount() {
        return paymentFee + brokerageFee + valueAddedFee;
    }

}
