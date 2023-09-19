package com.ray.pominowner.menu.domain;

import com.ray.pominowner.global.domain.BaseTimeEntity;
import com.ray.pominowner.global.util.ExceptionMessage;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import static com.ray.pominowner.global.util.Validator.validate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseTimeEntity {

    @Id
    @Column(name = "MENU_ID")
    @GeneratedValue
    private Long id;

    private String name;

    private String info;

    private Long menuImageId;

    private Long storeId;

    private int price;

    //    private Integer listOrder; // 추후 수정 예정

    @Builder
    public Menu(String name, String info, int price, Long menuImageId, Long storeId) {
        validateMenu(name, info, price);

        this.name = name;
        this.menuImageId = menuImageId;
        this.info = info;
        this.price = price;
        this.storeId = storeId;
    }

    private void validateMenu(String name, String info, int price) {
        Assert.hasText(name, "메뉴 형식이 올바르지 않습니다.");
        Assert.hasText(info, "메뉴 형식이 올바르지 않습니다.");
        validate(price < 0, ExceptionMessage.valueOf("메뉴 형식이 올바르지 않습니다."));
    }

    public Long getId() {
        return id;
    }

}
