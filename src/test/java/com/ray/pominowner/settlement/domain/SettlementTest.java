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

class SettlementTest {
    private static final Fee fee = new Fee(1000, 1000, 1000);
    private static final PayOut payOut = new PayOut(1000, LocalDate.now().plusDays(1));

    private static final Sales sales = new Sales(1000, LocalDate.now());

    @Test
    @DisplayName("Settlement 생성에 성공한다.")
    public void successSettlement() {
        // given, when
        Settlement settlement = new Settlement(fee, payOut, sales, 1L, 1L, 1L);

        // then
        assertThat(settlement).isNotNull();
    }

    @ParameterizedTest(name = "[{index}] amount : {0}, date : {1}")
    @MethodSource("invalidSettlement")
    @DisplayName("필드 값이 유효하지 않으면 Settlement 생성에 실패한다.")
    public void failSettlement(Fee fee, PayOut payOut, Sales sales) {
        assertThatThrownBy(() ->
                new Settlement(fee, payOut, sales, 1L, 1L, 1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> invalidSettlement() {
        return Stream.of(
                Arguments.arguments(null, null, null),
                Arguments.arguments(null, null, sales),
                Arguments.arguments(null, payOut, null),
                Arguments.arguments(null, payOut, sales),
                Arguments.arguments(fee, null, null),
                Arguments.arguments(fee, null, sales),
                Arguments.arguments(fee, payOut, null)
        );
    }
}
