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
import static com.ray.pominowner.global.util.Validator.validate;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PayOut {

    private int payOutAmount;

    private LocalDate payOutDate;

    public PayOut(int paymentAmount, Fee fee, LocalDate orderedAt) {
        validatePayOut(paymentAmount, fee);

        int payOutAmount = paymentAmount - fee.calculateTotalMinusAmount();
        validate(payOutAmount >= 0, INVALID_AMOUNT);  // 추후 처리 예정

        this.payOutAmount = payOutAmount;
        this.payOutDate = calculatePayoutDate(orderedAt);
    }

    private void validatePayOut(int paymentAmount, Fee fee) {
        Assert.notNull(fee, NULL_FEE_OBJECT.getMessage());
        validate(paymentAmount >= 0, INVALID_AMOUNT);
    }

    public LocalDate calculatePayoutDate(LocalDate orderedAt) {
        return orderedAt.plusDays(3);
    }

}
