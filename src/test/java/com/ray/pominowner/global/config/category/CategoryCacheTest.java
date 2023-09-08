package com.ray.pominowner.global.config.category;

import com.ray.pominowner.store.domain.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryCacheTest {

    @Test
    @DisplayName("카테고리 enum 목록대로 카테고리 리스트를 생성한다.")
    void successCreatingInitialCategoryList() {
        // given, when
        List<Category> categories = CategoryCache.cachedCategoryList();

        // then
        assertThat(categories).hasSize(InitialCategoryInfo.values().length);
    }

}
