package com.ray.pominowner.payment.domain;

import com.ray.pominowner.global.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ray.pominowner.global.util.ExceptionMessage.INVALID_AMOUNT;
import static com.ray.pominowner.global.util.ExceptionMessage.NULL_PAYMENT_PROVIDER;
import static com.ray.pominowner.global.util.ExceptionMessage.NULL_PAYMENT_STATUS;
import static com.ray.pominowner.global.util.Validator.validate;
import static java.util.Objects.isNull;

@Entity
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "PAYMENT_ID")
    private Long id;

    private int amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    private PGType provider;

    public Payment(int amount, PaymentStatus status, PGType provider) {
        validate(amount < 0, INVALID_AMOUNT);
        validate(isNull(status), NULL_PAYMENT_STATUS);
        validate(isNull(provider), NULL_PAYMENT_PROVIDER);

        this.amount = amount;
        this.status = status;
        this.provider = provider;
    }
}