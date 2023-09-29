package com.ray.pominowner.menu.domain;

import com.ray.pominowner.global.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class MenuOptionGroup extends BaseTimeEntity {

    @Id
    @Column(name = "MENU_OPTION_GROUP_ID")
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MENU_ID")
    private Menu menu;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "OPTION_GROUP_ID")
    private OptionGroup optionGroup;

    public MenuOptionGroup(Menu menu, OptionGroup optionGroup) {
        validateConstructor(menu, optionGroup);
        this.menu = menu;
        this.optionGroup = optionGroup;
    }

    private void validateConstructor(Menu menu, OptionGroup optionGroup) {
        Assert.notNull(menu, "메뉴는 필수입니다.");
        Assert.notNull(optionGroup, "옵션 그룹은 필수입니다.");
    }

}
