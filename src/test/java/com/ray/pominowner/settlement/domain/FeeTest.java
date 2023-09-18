package com.ray.pominowner.settlement.domain;

import com.ray.pominowner.payment.domain.PGType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.ray.pominowner.settlement.SettlementTestFixture.fee;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FeeTest {

    @Test
    @DisplayName("Fee 생성에 성공한다.")
    public void successFee() {
        assertThat(fee()).isNotNull();
    }

    @ParameterizedTest(name = "[{index}] pgType : {0}, payAmount : {1}")
    @MethodSource("invalidFee")
    @DisplayName("필드 값이 유효하지 않은 경우 Fee 생성에 실패한다.")
    public void failFee(PGType pgType, int payAmount) {
        assertThatThrownBy(() -> new Fee(pgType, payAmount))
                .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> invalidFee() {
        return Stream.of(
                Arguments.arguments(null, 1000),
                Arguments.arguments(null, -1000)
        );
    }

}
