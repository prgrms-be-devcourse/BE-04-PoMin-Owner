package com.ray.pominowner.store.domain;

import com.ray.pominowner.global.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public StoreCategory(final Store store, final Category category) {
        validateConstructor(store, category);
        this.store = store;
        this.category = category;
    }

    private void validateConstructor(final Store store, final Category category) {
        Assert.notNull(store, "가게는 null 일 수 없습니다.");
        Assert.notNull(category, "카테고리는 null 일 수 없습니다.");
    }
}
