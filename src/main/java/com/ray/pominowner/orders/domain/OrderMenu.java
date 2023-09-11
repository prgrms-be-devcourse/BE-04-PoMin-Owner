package com.ray.pominowner.orders.domain;

import com.ray.pominowner.global.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class OrderMenu extends BaseTimeEntity {

    @Id
    @Column(name = "ORDER_MENU_ID")
    @GeneratedValue
    private Long id;

    private String name;

    private Integer price;

    private Integer quantity;

    private String options;

    // Orders 와 다대일 매핑
    private Long orderId;

}
