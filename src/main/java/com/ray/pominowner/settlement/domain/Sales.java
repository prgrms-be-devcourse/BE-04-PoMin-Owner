package com.ray.pominowner.settlement.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.ray.pominowner.global.util.ExceptionMessage.INVALID_AMOUNT;
import static com.ray.pominowner.global.util.Validator.validate;


@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sales {

    private int salesAmount;

    private LocalDate salesDate;

    public Sales(int salesAmount, LocalDate orderedAt) {
        validate(salesAmount >= 0, INVALID_AMOUNT);

        this.salesAmount = salesAmount;
        this.salesDate = orderedAt;
    }

}
