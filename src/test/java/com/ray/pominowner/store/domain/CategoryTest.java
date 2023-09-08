package com.ray.pominowner.store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryTest {
    
    @Test
    @DisplayName("카테고리 enum 목록대로 카테고리 리스트를 생성한다.")
    void successCreatingInitialCategoryList() {
        // given, when
        List<Category> categories = Category.initialList();

        // then
        assertThat(categories).hasSize(CategoryInfo.values().length);
    }

}
