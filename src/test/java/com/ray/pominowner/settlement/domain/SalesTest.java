package com.ray.pominowner.settlement.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

    @Test
    @DisplayName("필드 값이 유효하지 않은 경우 Sales 생성에 실패한다.")
    public void failSales() {
        assertThatThrownBy(() -> new Sales(-1000, LocalDate.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
