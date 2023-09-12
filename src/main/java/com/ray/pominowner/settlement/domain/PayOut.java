package com.ray.pominowner.settlement.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.ray.pominowner.global.util.ExceptionMessage.INVALID_AMOUNT;
import static com.ray.pominowner.global.util.ExceptionMessage.PAYOUT_DATE_IS_BEFORE_NOW;
import static com.ray.pominowner.global.util.Validator.validate;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PayOut {

    private int payOutAmount;

    private LocalDate payOutDate;

    public PayOut(int payOutAmount, LocalDate payOutDate) {
        validate(payOutAmount < 0, INVALID_AMOUNT);
        validate(payOutDate.isBefore(LocalDate.now()), PAYOUT_DATE_IS_BEFORE_NOW);

        this.payOutAmount = payOutAmount;
        this.payOutDate = payOutDate;
    }

}
