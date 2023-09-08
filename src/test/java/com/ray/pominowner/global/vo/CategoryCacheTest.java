package com.ray.pominowner.global.vo;

import com.ray.pominowner.store.domain.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CategoryCacheTest {

    @Test
    @DisplayName("카테고리 리스트에 null이 포함되어 있지 않으면 캐시 저장에 성공한다")
    void successWhenAddCategoriesToCache() {
        // given
        CategoryCache cache = new CategoryCache();
        List<Category> categories = List.of(new Category("name", "imgurl"));

        // then
        assertThatNoException().isThrownBy(()-> cache.add(categories));
    }

    @ParameterizedTest
    @MethodSource(value = "categoryList")
    @NullSource
    @DisplayName("카테고리 리스트에 null이 포함되어 있으면 예외가 발생한다.")
    void failWhenCategoryListContainsNull(List<Category> categories) {
        // given
        CategoryCache cache = new CategoryCache();

        // when, then
        assertThatThrownBy(() -> cache.add(categories))
                .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> categoryList() {
        List<Category> categories = new ArrayList<>();
        categories.add(null);

        return Stream.of(
                Arguments.of(categories)
        );
    }

}
