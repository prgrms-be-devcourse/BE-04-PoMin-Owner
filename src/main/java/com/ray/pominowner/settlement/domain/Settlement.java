package com.ray.pominowner.settlement.domain;

import com.ray.pominowner.global.domain.BaseTimeEntity;
import com.ray.pominowner.global.util.Validator;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static com.ray.pominowner.global.util.ExceptionMessage.INVALID_PAYOUT_DATE;

@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Settlement extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "SETTLEMENT_ID")
    private Long id;

    @Embedded
    private Fee fee;

    @Embedded
    private PayOut payOut;

    @Embedded
    private Sales sales;

    private Long storeId;

    private Long orderId;

    private Long paymentId;

    public Settlement(Fee fee, PayOut payOut, Sales sales, Long storeId, Long orderId, Long paymentId) {
        Validator.validate(payOut.getAmount() < sales.getAmount(), INVALID_PAYOUT_DATE);

        this.fee = fee;
        this.payOut = payOut;
        this.sales = sales;
        this.storeId = storeId;
        this.orderId = orderId;
        this.paymentId = paymentId;
    }

}
