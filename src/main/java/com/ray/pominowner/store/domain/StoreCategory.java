package com.ray.pominowner.store.domain;

import com.ray.pominowner.global.domain.BaseTimeEntity;
import jakarta.persistence.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity
public class StoreCategory extends BaseTimeEntity {

    @Id
    @Column(name = "STORE_CATEGORY_ID")
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "STORE_ID")
    private Store store;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

}
