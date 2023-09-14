package com.ray.pominowner.store.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ray.pominowner.store.StoreTestFixture;
import com.ray.pominowner.store.domain.Store;
import com.ray.pominowner.store.repository.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @InjectMocks
    private StoreService storeService;

    @Mock
    private StoreServiceValidator storeServiceValidator;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private StoreCategoryService storeCategoryService;

    private Store store;

    @BeforeEach
    void setUp() {
        store = StoreTestFixture.store();
    }

    @Test
    @DisplayName("가게가 정상적으로 등록된다")
    void successRegisterStore() throws JsonProcessingException {
        // given
        doNothing().when(storeServiceValidator).validateBusinessNumber(store.getBusinessNumber());
        given(storeRepository.save(store)).willReturn(store);

        // when, then
        assertThatNoException()
                .isThrownBy(() -> storeService.registerStore(store));
    }


    @Test
    @DisplayName("카테고리가 정상적으로 등록된다")
    void successRegisterCategories() {
        // given
        List<String> categoryRequest = List.of("한식", "도시락");
        doNothing().when(storeServiceValidator).validateCategory(any());
        given(storeRepository.findById(store.getId())).willReturn(Optional.of(store));

        // when, then
        assertThatNoException()
                .isThrownBy(() -> storeService.registerCategory(categoryRequest, store.getId()));
    }

    @Test
    @DisplayName("전화번호가 정상적으로 등록된다")
    void successRegisterPhoneNumber() {
        // given
        String validPhoneNumber = "010-1234-5678";
        given(storeRepository.findById(store.getId())).willReturn(Optional.of(store));

        // when, then
        assertThatNoException()
                .isThrownBy(() -> storeService.registerPhoneNumber(validPhoneNumber, store.getId()));
    }

    @Test
    @DisplayName("전화번호를 정상적으로 삭제된다")
    void successDeletePhoneNumber() {
        // given
        given(storeRepository.findById(store.getId())).willReturn(Optional.of(store));

        // when, then
        assertThatNoException()
                .isThrownBy(() -> storeService.deletePhoneNumber(store.getId()));
    }

}
