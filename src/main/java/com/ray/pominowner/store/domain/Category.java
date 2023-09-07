package com.ray.pominowner.store.domain;

import com.ray.pominowner.global.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Category extends BaseTimeEntity {

    @Id
    @Column(name = "CATEGORY_ID")
    @GeneratedValue
    private Long id;

    private String name;

    private String image;

}
