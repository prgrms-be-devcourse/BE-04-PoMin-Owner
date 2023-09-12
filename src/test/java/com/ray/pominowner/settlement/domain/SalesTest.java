package com.ray.pominowner.settlement.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SalesTest {

    @Test
    @DisplayName("Sales 생성에 성공한다.")
    public void successSales() {
        // given, when
        Sales sales = new Sales(1000, LocalDate.now());

        // then
        assertThat(sales).isNotNull();
    }

    @ParameterizedTest(name = "[{index}] amount : {0}, date : {1}")
    @MethodSource("invalidSales")
    @DisplayName("필드 값이 유효하지 않은 경우 Sales 생성에 실패한다.")
    public void failSales(int amount, LocalDate date) {
        assertThatThrownBy(() -> new Sales(amount, date))
                .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> invalidSales() {
        return Stream.of(
                Arguments.arguments(1000, LocalDate.now().plusDays(1)),
                Arguments.arguments(-1000, LocalDate.now()),
                Arguments.arguments(-1000, LocalDate.now().plusDays(1))
        );
    }

}
