package com.ray.pominowner.store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CategoryInfoTest {

    private static final String URL_PREFIX = "src/main/resources/categoryimage/";
    public static final String URL_SUFFIX = ".png";

    @ParameterizedTest(name = "[{index}] Enum value : {0}")
    @EnumSource(value = CategoryInfo.class)
    @DisplayName("카테고리의 이름과 url이 잘 설정된다.")
    void successSettingCategoryInfo(CategoryInfo categoryInfo) {
        assertAll(
                () -> assertThat(categoryInfo.name())
                        .isEqualTo(categoryInfo.toString()),
                () -> assertThat(categoryInfo.url())
                        .isEqualTo(URL_PREFIX + categoryInfo + URL_SUFFIX)
        );
    }
}
