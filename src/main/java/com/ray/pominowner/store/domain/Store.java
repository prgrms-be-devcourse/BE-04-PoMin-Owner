package com.ray.pominowner.store.domain;

import com.ray.pominowner.global.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Store extends BaseTimeEntity {

    @Id
    @Column(name = "STORE_ID")
    @GeneratedValue
    private Long id;

    private Long businessNumber;

    private String name;

    private String info;

    private String tel;

    private String address;

    private String logoImage;

    private Long ownerId;

}
