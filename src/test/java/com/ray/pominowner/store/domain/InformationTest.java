package com.ray.pominowner.store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InformationTest {

    @ParameterizedTest(name = "입력값 : [{arguments}]")
    @ValueSource(strings = {"일", "", "  "})
    @NullSource
    @DisplayName("10글자 미만이면 예외가 발생한다")
    void failWhenUnderTenLetter(String information) {
        // when, then
        assertThatThrownBy(() -> new Information(information))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가게 정보는 10글자 이상이어야 합니다.");
    }

    @Test
    @DisplayName("10글자 이상이면 생성에 성공한다")
    void successWhenMoreThanTenLetter() {
        // given
        final String information = "일이삼사오육칠팔구십";

        // when, then
        assertThatNoException().isThrownBy(() -> new Information(information));
    }

}
