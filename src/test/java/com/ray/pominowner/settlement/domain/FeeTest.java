package com.ray.pominowner.settlement.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FeeTest {

    @Test
    @DisplayName("Fee 생성에 성공한다.")
    public void successFee() {
        // given, when
        Fee fee = new Fee(1000, 1000, 1000);

        // then
        assertThat(fee).isNotNull();
    }

    @ParameterizedTest(name = "[{index}] payment : {0}, brokerage : {1}, valueAdded : {2}")
    @MethodSource("invalidFee")
    @DisplayName("필드 값이 유효하지 않은 경우 Fee 생성에 실패한다.")
    public void failFee(int payment, int brokerage, int valueAdded) {
        assertThatThrownBy(() -> new Fee(payment, brokerage, valueAdded))
                .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> invalidFee() {
        return Stream.of(
                Arguments.arguments(-1000, -1000, -1000),
                Arguments.arguments(-1000, -1000, 1000),
                Arguments.arguments(-1000, 1000, -1000),
                Arguments.arguments(-1000, 1000, 1000),
                Arguments.arguments(1000, -1000, -1000),
                Arguments.arguments(1000, -1000, 1000),
                Arguments.arguments(1000, 1000, -1000)
        );
    }
}
