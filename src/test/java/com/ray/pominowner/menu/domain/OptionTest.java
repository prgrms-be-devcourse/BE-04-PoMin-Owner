package com.ray.pominowner.menu.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class OptionTest {

    private OptionGroup optionGroup;

    private List<Option> selectedOptions;

    @BeforeEach
    void setUp() {
        optionGroup = OptionGroup.builder()
                .name("첫번째 옵션 그룹")
                .maxOptionCount(3)
                .required(true)
                .storeId(1L)
                .build();

        Option firstOption = Option.builder()
                .name("옵션 1")
                .price(500)
                .selected(true)
                .optionGroup(optionGroup)
                .build();
        Option secondOption = Option.builder()
                .name("옵션 2")
                .price(700)
                .selected(true)
                .optionGroup(optionGroup)
                .build();
        Option thirdOption = Option.builder()
                .name("옵션 3")
                .price(1500)
                .selected(true)
                .optionGroup(optionGroup)
                .build();
        Option fourthOption = Option.builder()
                .name("옵션 4")
                .price(1500)
                .selected(true)
                .optionGroup(optionGroup)
                .build();

        selectedOptions = List.of(firstOption, secondOption, thirdOption, fourthOption);
    }

    @Test
    @DisplayName("옵선 생성에 성공한다")
    void successGeneratingOption() {

        // given
        Option firstOption = selectedOptions.get(0);

        // when
        optionGroup.add(firstOption);

        // then
        assertThat(optionGroup.getOptions())
                .hasSize(1)
                .contains(firstOption);
    }

    @Test
    @DisplayName("옵션 가격을 성공적으로 가져온다")
    void successCalculatingPrice() {
        // given
        Option firstOption = selectedOptions.get(0);
        Option secondOption = selectedOptions.get(1);
        Option thirdOption = selectedOptions.get(2);

        optionGroup.add(firstOption);
        optionGroup.add(secondOption);
        optionGroup.add(thirdOption);

        // when
        int totalPrice = optionGroup.getTotalPrice();

        // then
        int expectedPrice = 2_700;
        assertThat(totalPrice).isEqualTo(expectedPrice);
    }

    @Test
    @DisplayName("옵션 그룹의 최대 선택 옵션 개수를 초과해 옵션을 선택하면 예외가 발생한다")
    void failWhenExceedingMaxOptionCount() {
        // given
        Option firstOption = selectedOptions.get(0);
        Option secondOption = selectedOptions.get(1);
        Option thirdOption = selectedOptions.get(2);
        Option fourthOption = selectedOptions.get(3);

        optionGroup.add(firstOption);
        optionGroup.add(secondOption);
        optionGroup.add(thirdOption);

        // when, then
        assertThatThrownBy(() -> optionGroup.add(fourthOption))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("옵션 그룹의 최대 선택 옵션 개수를 초과하지 않으면 예외가 발생하지 않는다")
    void successWhenNotExceedingMaxOptionCount() {
        // given
        Option firstOption = selectedOptions.get(0);
        Option secondOption = selectedOptions.get(1);
        Option thirdOption = selectedOptions.get(2);
        Option unSelectedFourthOption = Option.builder()
                .name("옵션 4")
                .price(1500)
                .selected(false)
                .optionGroup(optionGroup)
                .build();

        optionGroup.add(firstOption);
        optionGroup.add(secondOption);
        optionGroup.add(thirdOption);

        // when, then
        assertThatNoException()
                .isThrownBy(() -> optionGroup.add(unSelectedFourthOption));
    }

    @Test
    @DisplayName("옵션의 선택 상태 변경에 성공한다")
    void successChangingOptionSelectionStatus() {
        // given
        Option firstOption = selectedOptions.get(0);

        Option unselectedOption = firstOption.changeSelectionStatus(false);


        // when, then
        assertThat(unselectedOption.isSelected()).isFalse();
    }

    @Test
    @DisplayName("옵션 그룹에서 옵션 선택이 필수일 경우, 옵션을 하나도 선택하지 않으면 예외가 발생한다")
    void failWhenOptionGroupStatusIsRequiredAndNotSelectingAnyOption() {
        Option firstOption = selectedOptions.get(0).changeSelectionStatus(false);
        Option secondOption = selectedOptions.get(1).changeSelectionStatus(false);
        Option thridOption = selectedOptions.get(2).changeSelectionStatus(false);

        optionGroup.add(firstOption);
        optionGroup.add(secondOption);
        optionGroup.add(thridOption);

        assertThatThrownBy(() -> optionGroup.checkRequiredOption())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("옵션 그룹에서 옵션 선택이 필수일 경우, 옵션을 하나라도 선택할 경우 예외가 발생하지 않는다")
    void successWhenOptionGroupStatausIsRequiredAndSelectingAtLeastOneOption() {
        // given
        Option firstOption = selectedOptions.get(0).changeSelectionStatus(false);
        Option secondOption = selectedOptions.get(1).changeSelectionStatus(false);
        Option thridOption = selectedOptions.get(2).changeSelectionStatus(true);

        optionGroup.add(firstOption);
        optionGroup.add(secondOption);
        optionGroup.add(thridOption);

        // when, then
        assertThatNoException()
                .isThrownBy(() -> optionGroup.checkRequiredOption());
    }

    @Test
    @DisplayName("옵션 그룹에서 옵션 선택이 필수가 아닐 경우, 옵션을 선택하지 않아도 예외가 발생하지 않는다")
    void successWhenOptionGroupStatausIsNotRequiredAndNotSelectingAnyOption() {
        // given
        OptionGroup optionGroup = OptionGroup.builder()
                .name("첫번째 옵션 그룹")
                .maxOptionCount(3)
                .required(false)
                .storeId(1L)
                .build();

        Option firstOption = selectedOptions.get(0).changeSelectionStatus(false);
        Option secondOption = selectedOptions.get(1).changeSelectionStatus(false);
        Option thridOption = selectedOptions.get(2).changeSelectionStatus(false);

        optionGroup.add(firstOption);
        optionGroup.add(secondOption);
        optionGroup.add(thridOption);

        // when, then
        assertThatNoException()
                .isThrownBy(optionGroup::checkRequiredOption);
    }

    @Test
    @DisplayName("옵션 그룹에서 옵션 선택이 필수가 아닐 경우, 옵션을 선택해도 예외가 발생하지 않는다")
    void successWhenOptionGroupStatausIsNotRequiredAndSelectingOption() {
        // given
        OptionGroup optionGroup = OptionGroup.builder()
                .name("첫번째 옵션 그룹")
                .maxOptionCount(3)
                .required(false)
                .storeId(1L)
                .build();

        Option firstOption = selectedOptions.get(0).changeSelectionStatus(true);
        Option secondOption = selectedOptions.get(1).changeSelectionStatus(true);
        Option thridOption = selectedOptions.get(2).changeSelectionStatus(true);

        optionGroup.add(firstOption);
        optionGroup.add(secondOption);
        optionGroup.add(thridOption);

        // when, then
        assertThatNoException()
                .isThrownBy(optionGroup::checkRequiredOption);
    }

}
