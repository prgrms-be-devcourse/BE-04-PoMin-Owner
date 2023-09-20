package com.ray.pominowner.settlement.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static com.ray.pominowner.settlement.SettlementTestFixture.sales;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SalesTest {

    @Test
    @DisplayName("Sales 생성에 성공한다.")
    public void successSales() {
        assertThat(sales()).isNotNull();
    }

    @ParameterizedTest(name = "[{index}] salesAmount : {0}")
    @ValueSource(ints = {1000, -1000})
    @DisplayName("필드 값이 유효하지 않은 경우 Sales 생성에 실패한다.")
    public void failSales(int salesAmount) {
        assertThatThrownBy(() -> new Sales(salesAmount, LocalDate.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
