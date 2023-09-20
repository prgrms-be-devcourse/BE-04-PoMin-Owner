package com.ray.pominowner.settlement;

import com.ray.pominowner.payment.domain.PGType;
import com.ray.pominowner.settlement.domain.DepositStatus;
import com.ray.pominowner.settlement.domain.Fee;
import com.ray.pominowner.settlement.domain.PayOut;
import com.ray.pominowner.settlement.domain.Sales;
import com.ray.pominowner.settlement.domain.ServiceType;
import com.ray.pominowner.settlement.domain.Settlement;

import java.time.LocalDate;

public final class SettlementTestFixture {

    public static Fee fee() {
        return new Fee(PGType.TOSS, 10000);
    }

    public static PayOut payOut() {
        return new PayOut(10000, fee(), LocalDate.now());
    }

    public static Sales sales() {
        return new Sales(10000, LocalDate.now());
    }

    public static Settlement settlement() {
        return Settlement.builder()
                .id(1L)
                .fee(fee())
                .payOut(payOut())
                .sales(sales())
                .depositStatus(DepositStatus.SCHEDULED)
                .serviceType(ServiceType.PACKAGING)
                .storeId(1L)
                .orderId(1L)
                .paymentId(1L)
                .deleted(false)
                .build();
    }

}
