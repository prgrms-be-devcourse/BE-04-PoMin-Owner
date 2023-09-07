package com.ray.pominowner.menu.domain;

import com.ray.pominowner.global.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Option extends BaseTimeEntity {

    @Id
    @Column(name = "OPTION_ID")
    @GeneratedValue
    private Long id;

    private String name;

    private int price;

    private Long optionGroupId;

}
