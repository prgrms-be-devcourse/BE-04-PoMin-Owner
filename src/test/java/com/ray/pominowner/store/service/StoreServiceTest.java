package com.ray.pominowner.store.service;

import com.ray.pominowner.store.StoreTestFixture;
import com.ray.pominowner.store.domain.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class StoreServiceTest {

    @Autowired
    private StoreService storeService;

    private Long storeId;

    @BeforeEach
    void setUp() {
        Store store = StoreTestFixture.store();
        storeId = storeService.registerStore(store);
    }

    @Test
    @DisplayName("카테고리가 정상적으로 등록된다")
    void successRegisterCategories() {
        // given
        List<String> categoryRequest = List.of("한식", "도시락");

        // when, then
        assertThatNoException()
                .isThrownBy(() -> storeService.registerCategory(categoryRequest, storeId));
    }

    @Test
    @DisplayName("카테고리 리스트가 null이면 예외가 발생한다.")
    void failIfCategoryListIsNull() {
        // given
        List<String> categoryRequest = null;

        // when, then
        assertThatThrownBy(() -> storeService.registerCategory(categoryRequest, storeId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("카테고리 리스트에 요소가 하나 이상이어야 합니다.");
    }

    @Test
    @DisplayName("카테고리 리스트에 요소가 하나 미만이면 예외가 발생한다.")
    void failWhenCategoryListElementIsLessThanOne() {
        // given
        List<String> categoryRequest = Collections.emptyList();

        // when, then
        assertThatThrownBy(() -> storeService.registerCategory(categoryRequest, storeId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("카테고리 리스트에 요소가 하나 이상이어야 합니다.");
    }

    @Test
    @DisplayName("카테고리 리스트에 실제 카테고리가 아닌 요소가 있으면 예외가 발생한다.")
    void failWhenCategoryListContainsWrongCategoryName() {
        // given
        List<String> categoryRequest = List.of("한식", "도시락", "존재하지않는카테고리");

        // when, then
        assertThatThrownBy(() -> storeService.registerCategory(categoryRequest, storeId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 카테고리가 포함되어 있습니다.");
    }

    @Test
    @DisplayName("카테고리 리스트에 중복된 요소가 있으면 예외가 발생한다.")
    void failWhenCategoryListContainsDuplicateElement() {
        // given
        List<String> categoryRequest = List.of("한식", "한식", "한식");

        // when, then
        assertThatThrownBy(() -> storeService.registerCategory(categoryRequest, storeId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중복된 카테고리가 있습니다.");
    }

}
