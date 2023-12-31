package com.ray.pominowner.payment.domain;

import com.ray.pominowner.global.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.util.Assert;

import java.util.Objects;

import static com.ray.pominowner.global.util.ExceptionMessage.INVALID_AMOUNT;
import static com.ray.pominowner.global.util.ExceptionMessage.NULL_PAYMENT_PROVIDER;
import static com.ray.pominowner.global.util.ExceptionMessage.NULL_PAYMENT_STATUS;
import static com.ray.pominowner.global.util.Validator.validate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseTimeEntity {

    @Id
    @Column(name = "PAYMENT_ID")
    private Long id;

    private int amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    private PGType provider;

    @Builder
    public Payment(Long id, int amount, PaymentStatus status, PGType provider) {
        validate(amount >= 0, INVALID_AMOUNT);
        Assert.notNull(status, NULL_PAYMENT_STATUS.getMessage());
        Assert.notNull(provider, NULL_PAYMENT_PROVIDER.getMessage());

        this.id = id;
        this.amount = amount;
        this.status = status;
        this.provider = provider;
    }

    public Payment canceled() {
        return Payment.builder()
                .id(this.id)
                .amount(this.amount)
                .status(PaymentStatus.CANCELED)
                .provider(this.provider)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Payment payment = (Payment) o;
        return id != null && Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
