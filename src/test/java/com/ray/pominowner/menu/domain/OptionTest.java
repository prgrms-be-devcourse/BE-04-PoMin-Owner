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
        optionGroup.addOption(firstOption);

        // then
        assertThat(optionGroup.getOptions())
                .hasSize(1)
                .contains(firstOption);
    }

    @Test
    @DisplayName("옵션 가격을 계산하는데 성공한다")
    void successCalculatingPrice() {
        // given
        Option firstOption = selectedOptions.get(0);
        Option secondOption = selectedOptions.get(1);
        Option thirdOption = selectedOptions.get(2);

        optionGroup.addOption(firstOption);
        optionGroup.addOption(secondOption);
        optionGroup.addOption(thirdOption);

        // when
        int totalPrice = optionGroup.getTotalPrice();

        // then
        int expectedPrice = 2_700;
        assertThat(totalPrice).isEqualTo(expectedPrice);
    }

    @Test
    @DisplayName("옵션 그룹의 최대 선택 옵션 개수를 초과해 옵션을 선택하면 옵션 추가에 실패한다")
    void failWhenExceedingMaxOptionCount() {
        // given
        Option firstOption = selectedOptions.get(0);
        Option secondOption = selectedOptions.get(1);
        Option thirdOption = selectedOptions.get(2);
        Option fourthOption = selectedOptions.get(3);

        optionGroup.addOption(firstOption);
        optionGroup.addOption(secondOption);
        optionGroup.addOption(thirdOption);

        // when, then
        assertThatThrownBy(() -> optionGroup.addOption(fourthOption))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("옵션 그룹의 최대 선택 옵션 개수를 초과하지 않으면 옵션 추가에 성공한다")
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

        optionGroup.addOption(firstOption);
        optionGroup.addOption(secondOption);
        optionGroup.addOption(thirdOption);

        // when, then
        assertThatNoException()
                .isThrownBy(() -> optionGroup.addOption(unSelectedFourthOption));
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
    @DisplayName("옵션 그룹에서 옵션 선택이 필수일 경우, 옵션을 하나도 선택하지 않으면 필수 옵션 검증 통과에 실페한다")
    void failWhenOptionGroupStatusIsRequiredAndNotSelectingAnyOption() {
        // given
        Option firstOption = selectedOptions.get(0).changeSelectionStatus(false);
        Option secondOption = selectedOptions.get(1).changeSelectionStatus(false);
        Option thridOption = selectedOptions.get(2).changeSelectionStatus(false);

        optionGroup.addOption(firstOption);
        optionGroup.addOption(secondOption);
        optionGroup.addOption(thridOption);

        // when, then
        assertThatThrownBy(() -> optionGroup.checkRequiredOption())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("옵션 그룹에서 옵션 선택이 필수일 경우, 옵션을 하나라도 선택할 경우 옵션 검증 통과에 성공한다")
    void successWhenOptionGroupStatusIsRequiredAndSelectingAtLeastOneOption() {
        // given
        Option firstOption = selectedOptions.get(0).changeSelectionStatus(false);
        Option secondOption = selectedOptions.get(1).changeSelectionStatus(false);
        Option thridOption = selectedOptions.get(2).changeSelectionStatus(true);

        optionGroup.addOption(firstOption);
        optionGroup.addOption(secondOption);
        optionGroup.addOption(thridOption);

        // when, then
        assertThatNoException()
                .isThrownBy(() -> optionGroup.checkRequiredOption());
    }

    @Test
    @DisplayName("옵션 그룹에서 옵션 선택이 필수가 아닐 경우, 옵션을 선택하지 않아도 옵션 검증 통과에 성공한다")
    void successWhenOptionGroupStatusIsNotRequiredAndNotSelectingAnyOption() {
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

        optionGroup.addOption(firstOption);
        optionGroup.addOption(secondOption);
        optionGroup.addOption(thridOption);

        // when, then
        assertThatNoException()
                .isThrownBy(optionGroup::checkRequiredOption);
    }

    @Test
    @DisplayName("옵션 그룹에서 옵션 선택이 필수가 아닐 경우, 옵션을 선택해도 옵션 검증 통과에 성공한다")
    void successWhenOptionGroupStatusIsNotRequiredAndSelectingOption() {
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

        optionGroup.addOption(firstOption);
        optionGroup.addOption(secondOption);
        optionGroup.addOption(thridOption);

        // when, then
        assertThatNoException()
                .isThrownBy(optionGroup::checkRequiredOption);
    }
    
    @Test
    @DisplayName("옵션 그룹에 옵션 개수가 10개를 초과하면 옵션 추가에 실패한다")
    void failAddOptionWhenOptionCountIsOverTen() {
        // given
        Option firstOption = selectedOptions.get(0).changeSelectionStatus(false);
        Option secondOption = selectedOptions.get(1).changeSelectionStatus(false);
        Option thridOption = selectedOptions.get(2).changeSelectionStatus(false);
        Option fourthOption = selectedOptions.get(3).changeSelectionStatus(false);
        Option fifthOption = Option.builder()
                .name("옵션 5")
                .price(1000)
                .selected(false)
                .optionGroup(optionGroup)
                .build();
        Option sixthOption = Option.builder()
                .name("옵션 6")
                .price(1000)
                .selected(false)
                .optionGroup(optionGroup)
                .build();
        Option seventhOption = Option.builder()
                .name("옵션 7")
                .price(1000)
                .selected(false)
                .optionGroup(optionGroup)
                .build();
        Option eighthOption = Option.builder()
                .name("옵션 8")
                .price(1000)
                .selected(false)
                .optionGroup(optionGroup)
                .build();
        Option ninthOption = Option.builder()
                .name("옵션 9")
                .price(1000)
                .selected(false)
                .optionGroup(optionGroup)
                .build();
        Option tenthOption = Option.builder()
                .name("옵션 10")
                .price(1000)
                .selected(false)
                .optionGroup(optionGroup)
                .build();
        Option eleventhOption = Option.builder()
                .name("옵션 11")
                .price(1000)
                .selected(false)
                .optionGroup(optionGroup)
                .build();

        optionGroup.addOption(firstOption);
        optionGroup.addOption(secondOption);
        optionGroup.addOption(thridOption);
        optionGroup.addOption(fourthOption);
        optionGroup.addOption(fifthOption);
        optionGroup.addOption(sixthOption);
        optionGroup.addOption(seventhOption);
        optionGroup.addOption(eighthOption);
        optionGroup.addOption(ninthOption);
        optionGroup.addOption(tenthOption);

        // when, then
        assertThatThrownBy(() -> optionGroup.addOption(eleventhOption))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
