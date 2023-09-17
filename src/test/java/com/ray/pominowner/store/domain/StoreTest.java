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
    @DisplayName("전화번호 설저에 성공한다")
    void successRegisterPhoneNumber() {
        // given
        Store store = StoreTestFixture.store();
        PhoneNumber validPhoneNumber = new PhoneNumber("010-1234-5678");

        // when
        Store storeAfterRegisteredPhoneNumber = store.retrieveStoreAfterRegisteringPhoneNumber(validPhoneNumber.getTel());

        // then
        assertThat(store.getPhoneNumber()).isEqualTo(new PhoneNumber());
        assertThat(storeAfterRegisteredPhoneNumber.getPhoneNumber()).isEqualTo(validPhoneNumber);
    }

    @Test
    @DisplayName("전화번호 삭제에 성공한다")
    void successDeletePhoneNumber() {
        // given
        String validPhoneNumber = "010-1234-5678";
        Store store = StoreTestFixture.store()
                .retrieveStoreAfterRegisteringPhoneNumber(validPhoneNumber);

        // when
        Store deletedPhoneNumberStore = store.retrieveStoreAfterDeletingPhoneNumber();

        // then
        assertThat(deletedPhoneNumberStore.getPhoneNumber()).isEqualTo(new PhoneNumber());
        assertThat(deletedPhoneNumberStore.getPhoneNumber()).isNotEqualTo(new PhoneNumber(validPhoneNumber));
    }

    @Test
    @DisplayName("가게 정보 설정에 성공한다")
    void successRegisterInformation() {
        // given
        Store store = StoreTestFixture.store();
        Information validInformation = new Information("가게 정보입니다. 테스트 용도입니다.");

        // when
        Store storeAfterRegisteredPhoneNumber = store.retrieveStoreAfterRegisteringInfo(validInformation.getInfo());

        // then
        assertThat(store.getInformation()).isEqualTo(new Information());
        assertThat(storeAfterRegisteredPhoneNumber.getInformation()).isEqualTo(validInformation);
    }

    @Test
    @DisplayName("가게 정보 삭제에 성공한다")
    void successDeletingInformation() {
        // given
        String validInformation = "가게 정보입니다. 테스트 용도입니다.";
        Store store = StoreTestFixture.store()
                .retrieveStoreAfterRegisteringInfo(validInformation);

        // when
        Store deletedInfoStore = store.retrieveStoreAfterDeletingInfo();

        // then
        assertThat(deletedInfoStore.getInformation()).isEqualTo(new Information());
        assertThat(deletedInfoStore.getInformation()).isNotEqualTo(new Information(validInformation));
    }

}
