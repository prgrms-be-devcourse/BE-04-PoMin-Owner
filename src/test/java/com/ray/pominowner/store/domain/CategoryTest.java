package com.ray.pominowner.store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CategoryTest {

    @ParameterizedTest(name = "[{index}] name : {0} / url : {1}")
    @MethodSource("categoryInfo")
    @DisplayName("이름과 이미지에 값이 존재하지 않으면 엔티티 생성에 실패한다")
    void failWhenHasNoText(String name, String image) {
        assertThatThrownBy(() -> new Category(name, image))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이름과 이미지에 값이 존재하면 엔티티 생성에 성공한다")
    void successWhenHasText() {
        assertThatNoException()
                .isThrownBy(() -> new Category("name", "image"));
    }

    static Stream<Arguments> categoryInfo() {
        return Stream.of(
                Arguments.arguments(null, null),
                Arguments.arguments("name", null),
                Arguments.arguments(null, "image.png"),
                Arguments.arguments("name", ""),
                Arguments.arguments("name", " "),
                Arguments.arguments("name", "   "),
                Arguments.arguments("", "image.png"),
                Arguments.arguments(" ", "image.png"),
                Arguments.arguments("    ", "image.png")
        );
    }

}
