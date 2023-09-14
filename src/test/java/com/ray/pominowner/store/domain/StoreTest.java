package com.ray.pominowner.store.domain;

import com.ray.pominowner.store.StoreTestFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
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
        RequiredStoreInfo requiredStoreInfo = StoreTestFixture.requiredStoreInfo();

        // when, then
        assertThatNoException()
                .isThrownBy(() -> new Store(requiredStoreInfo));
    }

    @Test
    @DisplayName("전화번호를 등록하면 전화번호가 설정된다")
    void successRegisterPhoneNumber() {
        // given
        Store store = StoreTestFixture.store();
        String validPhoneNumber = "01012345678";

        // when
        Store storeAfterRegisteredPhoneNumber = store.afterRegisterPhoneNumber(validPhoneNumber);

        // then
        assertThat(store.getTel()).isEqualTo("NOT_SELECTED");
        assertThat(storeAfterRegisteredPhoneNumber.getTel()).isEqualTo(validPhoneNumber);
    }

}
