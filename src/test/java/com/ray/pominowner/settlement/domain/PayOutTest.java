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
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PayOutTest {

    @Test
    @DisplayName("Payout 생성에 성공한다.")
    public void successPayout() {
        assertThat(payOut()).isNotNull();
    }

    @ParameterizedTest(name = "[{index}] paymentAmount : {0}, fee: {1}")
    @MethodSource("invalidPayout")
    @DisplayName("필드 값이 유효하지 않은 경우 Payout 생성에 실패한다.")
    public void failPayout(int paymentAmount, Fee fee) {
        assertThatThrownBy(() -> new PayOut(paymentAmount, fee, LocalDate.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> invalidPayout() {
        return Stream.of(
                Arguments.arguments(-1000, null),
                Arguments.arguments(-1000, fee()),
                Arguments.arguments(1000, null)
        );
    }

}
