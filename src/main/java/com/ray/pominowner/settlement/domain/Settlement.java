package com.ray.pominowner.settlement.domain;

import com.ray.pominowner.global.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static com.ray.pominowner.global.util.ExceptionMessage.NULL_FEE_OBJECT;
import static com.ray.pominowner.global.util.ExceptionMessage.NULL_PAYOUT_OBJECT;
import static com.ray.pominowner.global.util.ExceptionMessage.NULL_SALES_OBJECT;
import static com.ray.pominowner.global.util.Validator.validate;
import static java.util.Objects.isNull;

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
        validate(isNull(fee), NULL_FEE_OBJECT);
        validate(isNull(payOut), NULL_PAYOUT_OBJECT);
        validate(isNull(sales), NULL_SALES_OBJECT);

        this.fee = fee;
        this.payOut = payOut;
        this.sales = sales;
        this.storeId = storeId;
        this.orderId = orderId;
        this.paymentId = paymentId;
    }

}
