package com.ray.pominowner.store.domain;

import com.ray.pominowner.store.DataLoader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StoreTest {

    @Test
    @DisplayName("필수정보가 null이면 예외를 발생한다")
    void failWhenRequiredInfoIsNull() {
        // given
        RequiredStoreInfo requiredStoreInfo = null;

        // when, then
        assertThrows(IllegalArgumentException.class, () -> new Store(requiredStoreInfo));
    }

    @Test
    @DisplayName("필수정보가 null이 아니면 예외를 발생하지 않는다")
    void successWhenRequiredInfoIsNotNull() {
        // given
        RequiredStoreInfo requiredStoreInfo = DataLoader.requiredStoreInfo();

        // when, then
        assertThatNoException()
                .isThrownBy(() -> new Store(requiredStoreInfo));
    }

}
