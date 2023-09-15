package com.ray.pominowner.settlement.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.LocalDate;

import static com.ray.pominowner.global.util.ExceptionMessage.INVALID_AMOUNT;
import static com.ray.pominowner.global.util.ExceptionMessage.NULL_FEE_OBJECT;
import static com.ray.pominowner.global.util.ExceptionMessage.PAYOUT_DATE_IS_BEFORE_NOW;
import static com.ray.pominowner.global.util.Validator.validate;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PayOut {

    private int payOutAmount;

    private LocalDate payOutDate;

    public PayOut(int paymentAmount, Fee fee, LocalDate payOutDate) {
        Assert.notNull(fee, NULL_FEE_OBJECT.getMessage());
        validate(paymentAmount >= 0, INVALID_AMOUNT);
        validate(payOutDate.isAfter(LocalDate.now()) || payOutDate.isEqual(LocalDate.now()), PAYOUT_DATE_IS_BEFORE_NOW);

        int payOutAmount = paymentAmount - fee.calculateTotalMinusAmount();
        validate(payOutAmount >= 0, INVALID_AMOUNT);  // 추후 처리 예정

        this.payOutAmount = payOutAmount;
        this.payOutDate = payOutDate;
    }

    public LocalDate calculatePayoutDate(LocalDate orderedAt) {
        // 추후 구현 예정
        return orderedAt;
    }

}
