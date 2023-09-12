package com.ray.pominowner.store.domain;

import com.ray.pominowner.store.StoreTestFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StoreCategoryTest {

    @Test
    @DisplayName("가게 엔티티가 null이면 예외를 발생한다")
    void failWhenStoreEntityIsNull() {
        // given
        Store store = null;
        Category category = StoreTestFixture.category();

        // when, then
        assertThrows(IllegalArgumentException.class, () -> new StoreCategory(store, category));
    }

    @Test
    @DisplayName("카테고리 엔티티가 null이면 예외를 발생한다")
    void failWhenCategoryEntityIsNull() {
        // given
        Store store = StoreTestFixture.store();
        Category category = null;
        // when, then
        assertThrows(IllegalArgumentException.class, () -> new StoreCategory(store, category));
    }

    @Test
    @DisplayName("가게 엔티티와 카테고리 엔티티가 모두 null이 아니면 정상적으로 생성된다")
    void successWhenStoreAndCategoryEntityIsNotNull() {
        // given
        Store store = StoreTestFixture.store();
        Category category = StoreTestFixture.category();

        // when, then
        assertThatNoException()
                .isThrownBy(() -> new StoreCategory(store, category));
    }

}
