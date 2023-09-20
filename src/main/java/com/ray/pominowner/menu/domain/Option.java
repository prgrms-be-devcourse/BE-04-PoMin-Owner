package com.ray.pominowner.menu.domain;

import com.ray.pominowner.global.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = PROTECTED)
public class Option extends BaseTimeEntity {

    @Id
    @Column(name = "OPTION_ID")
    @GeneratedValue
    private Long id;

    private String name;

    private int price;

    private boolean selected;

    @ManyToOne
    @JoinColumn(name = "OPTION_GROUP_ID")
    private OptionGroup optionGroup;

    @Builder
    private Option(Long id, String name, int price, boolean selected, OptionGroup optionGroup) {
        validateConstructor(name, price, optionGroup);

        this.id = id;
        this.name = name;
        this.price = price;
        this.selected = selected;
        this.optionGroup = optionGroup;
    }

    private void validateConstructor(String name, int price, OptionGroup optionGroup) {
        Assert.hasText(name, "옵션 이름은 필수입니다.");
        Assert.isTrue(price >= 0, "잘못된 가격을 입력하셨습니다.");
        Assert.notNull(optionGroup, "옵션 그룹 선택은 필수입니다.");
    }

    public Option changeSelectionStatus(boolean selected) {
        if (this.selected == selected) {
           return this;
        }

        return Option.builder()
                .id(this.id)
                .name(this.name)
                .price(this.price)
                .selected(selected)
                .optionGroup(this.optionGroup)
                .build();
    }

}
