package com.ray.pominowner.global.config.category;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class InitialCategoryInfoTest {

    private static final String URL_PREFIX = "src/main/resources/categoryimage/";
    public static final String URL_SUFFIX = ".png";

    @ParameterizedTest(name = "[{index}] Enum value : {0}")
    @EnumSource(value = InitialCategoryInfo.class)
    @DisplayName("카테고리의 이름과 url이 잘 설정된다")
    void successSettingCategoryInfo(InitialCategoryInfo initialCategoryInfo) {
        assertAll(
                () -> assertThat(initialCategoryInfo.name())
                        .isEqualTo(initialCategoryInfo.toString()),
                () -> assertThat(initialCategoryInfo.url())
                        .isEqualTo(URL_PREFIX + initialCategoryInfo + URL_SUFFIX)
        );
    }
}
