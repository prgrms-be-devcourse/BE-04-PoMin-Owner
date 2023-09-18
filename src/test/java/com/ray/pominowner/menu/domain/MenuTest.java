package com.ray.pominowner.menu.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuTest {

    @ParameterizedTest(name = "[{index}] 가격: {0}")
    @ValueSource(ints = {-1, -100, -1000})
    @DisplayName("가격이 0미만이면 예외가 발생한다")
    void failWhenPriceInNegative(int price) {
        assertThatThrownBy(
                () -> Menu.builder()
                        .name("메뉴이름")
                        .info("메뉴 설명")
                        .price(price)
                        .menuImageId(1L)
                        .storeId(1L)
                        .build()
        )
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("성공적으로 메뉴 엔티티가 생성된다")
    void successGeneratingMenuEntity() {
        assertThatNoException()
                .isThrownBy(() -> Menu.builder().name("메뉴이름")
                        .info("메뉴 설명")
                        .price(0)
                        .menuImageId(1L)
                        .storeId(1L));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    @NullSource
    @DisplayName("메뉴 이름이 null이거나 공백이면 예외가 발생한다")
    void failWhenMenuNameHasNoText(String menuName) {
        assertThatThrownBy(
                () -> Menu.builder()
                        .name(menuName)
                        .info("메뉴 설명")
                        .price(1000)
                        .menuImageId(1L)
                        .storeId(1L)
                        .build()
        )
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    @NullSource
    @DisplayName("메뉴 설명이 null이거나 공백이면 예외가 발생한다")
    void failWhenMenuInfoHasNoText(String menuInfo) {
        assertThatThrownBy(
                () -> Menu.builder()
                        .name("메뉴 이름")
                        .info(menuInfo)
                        .price(1000)
                        .menuImageId(1L)
                        .storeId(1L)
                        .build()
        )
                .isInstanceOf(IllegalArgumentException.class);
    }

}
