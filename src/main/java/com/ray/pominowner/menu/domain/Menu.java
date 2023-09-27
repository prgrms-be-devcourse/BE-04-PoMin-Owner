package com.ray.pominowner.menu.domain;

import com.ray.pominowner.global.domain.BaseTimeEntity;
import com.ray.pominowner.global.util.ExceptionMessage;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import static com.ray.pominowner.global.util.Validator.validate;
import static jakarta.persistence.FetchType.EAGER;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = PROTECTED)
public class Menu extends BaseTimeEntity {

    @Id
    @Column(name = "MENU_ID")
    @GeneratedValue
    private Long id;

    private String name;

    private String info;

    private int price;

    private Long storeId;

    @OneToOne(fetch = EAGER, orphanRemoval = true)
    @JoinColumn(name = "MENU_IMAGE_ID")
    private MenuImage image;

    //    private Integer listOrder; // 추후 수정 예정

    @Builder
    public Menu(Long id, String name, String info, int price, Long storeId, MenuImage image) {
        validateMenu(name, info, price);

        this.id = id;
        this.name = name;
        this.image = image;
        this.info = info;
        this.price = price;
        this.storeId = storeId;
    }

    private void validateMenu(String name, String info, int price) {
        Assert.hasText(name, ExceptionMessage.INVALID_MENU.getMessage());
        Assert.hasText(info, ExceptionMessage.INVALID_MENU.getMessage());
        validate(price >= 0, ExceptionMessage.INVALID_MENU);
    }

    public Menu updateMenu(Menu menu) {
        validateStoreId(menu);

        return Menu.builder()
                .id(id)
                .name(menu.name)
                .info(menu.info)
                .price(menu.price)
                .image(menu.image)
                .storeId(storeId)
                .build();
    }

    private void validateStoreId(Menu menu) {
        if (isNotValidStoreId(menu)){
            throw new IllegalArgumentException("유효한 storeId가 아닙니다.");
        }
    }

    private boolean isNotValidStoreId(Menu menu) {
        return !menu.storeId
                .equals(this.storeId);
    }

}
