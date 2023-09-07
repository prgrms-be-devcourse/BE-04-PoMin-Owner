package com.ray.pominowner.menu.domain;

import com.ray.pominowner.global.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Menu extends BaseTimeEntity {

    @Id
    @Column(name = "MENU_ID")
    @GeneratedValue
    private Long id;

    private String name;

    private String image;

    private String info;

    private Integer listOrder;

    private int price;

    private Long storeId;

}
