package com.ray.pominowner.settlement.domain;

import com.ray.pominowner.global.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.util.Assert;

import java.util.Objects;

import static com.ray.pominowner.global.util.ExceptionMessage.NULL_DEPOSIT_STATUS;
import static com.ray.pominowner.global.util.ExceptionMessage.NULL_FEE_OBJECT;
import static com.ray.pominowner.global.util.ExceptionMessage.NULL_PAYOUT_OBJECT;
import static com.ray.pominowner.global.util.ExceptionMessage.NULL_SALES_OBJECT;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Settlement extends BaseTimeEntity {

    @Id
    @Column(name = "SETTLEMENT_ID")
    private Long id;

    @Embedded
    private Fee fee;

    @Embedded
    private PayOut payOut;

    @Embedded
    private Sales sales;

    private DepositStatus depositStatus;

    private Long storeId;

    private Long orderId;

    private Long paymentId;

    private boolean deleted;

    @Builder
    public Settlement(Long id, Fee fee, PayOut payOut, Sales sales, DepositStatus depositStatus, Long storeId, Long orderId, Long paymentId, boolean deleted) {
        validateSettlement(fee, payOut, sales, depositStatus);

        this.id = id;
        this.fee = fee;
        this.payOut = payOut;
        this.sales = sales;
        this.depositStatus = depositStatus;
        this.storeId = storeId;
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.deleted = deleted;
    }

    private void validateSettlement(Fee fee, PayOut payOut, Sales sales, DepositStatus depositStatus) {
        Assert.notNull(fee, NULL_FEE_OBJECT.getMessage());
        Assert.notNull(payOut, NULL_PAYOUT_OBJECT.getMessage());
        Assert.notNull(sales, NULL_SALES_OBJECT.getMessage());
        Assert.notNull(depositStatus, NULL_DEPOSIT_STATUS.getMessage());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Settlement that = (Settlement) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
