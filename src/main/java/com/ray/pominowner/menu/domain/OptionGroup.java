package com.ray.pominowner.menu.domain;

import com.ray.pominowner.global.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class OptionGroup extends BaseTimeEntity {

    @Id
    @Column(name = "OPTION_GROUP_ID")
    @GeneratedValue
    private Long id;

    private String name;

    private int maxOptionCount;

    private boolean required;

    private Long storeId;

    // Option과 양방향 매핑

}
