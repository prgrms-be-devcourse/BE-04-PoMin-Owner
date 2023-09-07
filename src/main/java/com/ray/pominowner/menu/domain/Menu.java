package com.ray.pominowner.menu.domain;

import com.ray.pominowner.global.domain.BaseTimeEntity;
import jakarta.persistence.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity
public class Menu extends BaseTimeEntity {

    @Id
    @Column(name = "MENU_ID")
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private String image;

    private Integer listOrder;

    private int price;

    private Long storeId;

    // 옵션 group 양방향 매핑

}
