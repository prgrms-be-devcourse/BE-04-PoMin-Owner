package com.ray.pominowner.payment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PaymentTest {

    @Test
    @DisplayName("Payment 생성에 성공한다.")
    public void successPayment() {
        // given, when
        Payment payment = new Payment(1000, PaymentStatus.COMPLETE, PGType.TOSS);

        // then
        assertThat(payment).isNotNull();
    }

    @ParameterizedTest(name = "[{index}] amount : {0}, status : {1}, provider : {2}")
    @MethodSource("invalidPayment")
    @DisplayName("필드 값이 유효하지 않은 경우 Payment 생성에 실패한다.")
    public void failPayment(int amount, PaymentStatus status, PGType provider) {
        assertThatThrownBy(() -> new Payment(amount, status, provider))
                .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> invalidPayment() {
        return Stream.of(
                Arguments.arguments(1000, PaymentStatus.COMPLETE, null),
                Arguments.arguments(1000, null, PGType.TOSS),
                Arguments.arguments(1000, null, null),
                Arguments.arguments(-1000, PaymentStatus.COMPLETE, PGType.TOSS),
                Arguments.arguments(-1000, PaymentStatus.COMPLETE, null),
                Arguments.arguments(-1000, null, PGType.TOSS),
                Arguments.arguments(-1000, null, null)
        );
    }
}
