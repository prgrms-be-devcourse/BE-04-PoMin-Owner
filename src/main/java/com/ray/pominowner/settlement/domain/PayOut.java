package com.ray.pominowner.settlement.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.ray.pominowner.global.util.ExceptionMessage.INVALID_AMOUNT;
import static com.ray.pominowner.global.util.Validator.validate;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PayOut {

    private int amount;

    private LocalDate date;

    public PayOut(int amount, LocalDate date) {
        validate(amount < 0, INVALID_AMOUNT);

        this.amount = amount;
        this.date = date;
    }

}
