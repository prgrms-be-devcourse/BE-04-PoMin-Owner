package com.ray.pominowner.store.domain;

import com.ray.pominowner.global.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class StoreImage extends BaseTimeEntity {

    @Id
    @Column(name = "STORE_IAMGE_ID")
    @GeneratedValue
    private Long id;

    private String image;

    private Long storeId;

}
