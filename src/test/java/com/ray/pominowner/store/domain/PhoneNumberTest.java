package com.ray.pominowner.store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PhoneNumberTest {

    @ParameterizedTest
    @ValueSource(strings = {"abcdef", "0212345678", "02", "", "  "})
    @NullSource
    @DisplayName("전화번호 형식이 올바르지 않으면 예외가 발생한다")
    void failWhenIncorrectPhoneNumberFormat(String phoneNumber) {
        // when, then
        assertThatThrownBy(() -> new PhoneNumber(phoneNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바른 전화번호를 입력해 주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"010-1234-5678", "01012345678", "011-111-1111"})
    @DisplayName("전화번호 형식이 올바르면 예외가 발생하지 않는다")
    void successWhenCorrectPhoneNumberFormat(String phoneNumber) {
        // when, then
        assertThatNoException().isThrownBy(() -> new PhoneNumber(phoneNumber));
    }

}
