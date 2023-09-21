package com.ray.pominowner.settlement.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static com.ray.pominowner.settlement.SettlementTestFixture.fee;
import static com.ray.pominowner.settlement.SettlementTestFixture.payOut;
import static com.ray.pominowner.settlement.SettlementTestFixture.sales;
import static com.ray.pominowner.settlement.SettlementTestFixture.settlement;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SettlementTest {

    private final PayOut invalidPayOut = new PayOut(10000, fee(), LocalDate.now());

    private final Sales invalidSales = new Sales(1000, LocalDate.now().plusDays(5));


    @Test
    @DisplayName("Settlement 생성에 성공한다.")
    public void successSettlement() {
        assertThat(settlement()).isNotNull();
    }

    @ParameterizedTest(name = "[{index}] fee : {0}, payOut : {1}, sales: {2}")
    @MethodSource("invalidSettlement")
    @DisplayName("필드 값이 유효하지 않으면 Settlement 생성에 실패한다.")
    public void failSettlement(Fee fee, PayOut payOut, Sales sales) {
        assertThatThrownBy(() ->
                Settlement.builder()
                        .id(1L)
                        .fee(fee)
                        .payOut(payOut)
                        .sales(sales)
                        .depositStatus(DepositStatus.SCHEDULED)
                        .serviceType(ServiceType.PACKAGING)
                        .storeId(1L)
                        .orderId(1L)
                        .paymentId(1L)
                        .deleted(false)
                        .build())
                .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> invalidSettlement() {
        return Stream.of(
                Arguments.arguments(null, null, null),
                Arguments.arguments(null, null, sales()),
                Arguments.arguments(null, payOut(), null),
                Arguments.arguments(null, payOut(), sales()),
                Arguments.arguments(fee(), null, null),
                Arguments.arguments(fee(), null, sales()),
                Arguments.arguments(fee(), payOut(), null)
        );
    }
    
    @Test
    @DisplayName("지급일이 매출일보다 빠르면 Settlement 생성에 실패한다.")
    public void failSettlementWhenPayOutDateIsBeforeThanSalesDate() {
        assertThatThrownBy(() ->
                Settlement.builder()
                        .id(1L)
                        .fee(fee())
                        .payOut(invalidPayOut)
                        .sales(invalidSales)
                        .depositStatus(DepositStatus.SCHEDULED)
                        .serviceType(ServiceType.PACKAGING)
                        .storeId(1L)
                        .orderId(1L)
                        .paymentId(1L)
                        .deleted(false)
                        .build())
                .isInstanceOf(IllegalArgumentException.class);
    }

}
