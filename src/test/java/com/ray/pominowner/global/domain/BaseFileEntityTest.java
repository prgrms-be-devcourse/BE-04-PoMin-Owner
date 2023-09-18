package com.ray.pominowner.global.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BaseFileEntityTest {

    static class BaseFileEntityFixture extends BaseFileEntity {

        public BaseFileEntityFixture(String path, String uploadName, String fileName) {
            validateImage(path, uploadName, fileName);

            this.path = path;
            this.uploadName = uploadName;
            this.fileName = fileName;
        }

    }

    @Test
    @DisplayName("이미지 경로, 업로드 파일 이름, 저장 파일이름이 빈값, null이 아니면 엔티티 생성에 성공한다")
    void successWhenAllElementIsNotNullOrEmpty() {
        // given
        final String path = "path";
        final String uploadFileName = "uploadFileName";
        final String saveFileName = "saveFileName";

        // when, then
        assertThatNoException()
                .isThrownBy(() -> new BaseFileEntityFixture(path, uploadFileName, saveFileName));
    }

    @ParameterizedTest(name = "[{index}] path : {0} / uploadFileName : {1} / saveFileName : {2}")
    @DisplayName("이미지 경로, 업로드 파일 이름, 저장 파일이름이 빈값이거나 null이면 엔티티 생성이 실패한다")
    @MethodSource("storeImageInfo")
    void failWhenElementIsNullOrEmpty(String path, String uploadFileName, String saveFileName) {
        // when, then
        assertThatThrownBy(() -> new BaseFileEntityFixture(path, uploadFileName, saveFileName))
                .isInstanceOf(IllegalArgumentException.class);

    }

    static Stream<Arguments> storeImageInfo() {
        return Stream.of(
                Arguments.arguments(null, null, null),
                Arguments.arguments("", "", ""),
                Arguments.arguments(" ", " ", " "),
                Arguments.arguments("   ", "   ", "   ")
        );
    }

}
