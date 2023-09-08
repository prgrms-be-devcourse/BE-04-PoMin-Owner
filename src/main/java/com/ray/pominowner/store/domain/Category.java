package com.ray.pominowner.store.domain;

import com.ray.pominowner.global.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseTimeEntity {

    public static List<Category> INITIAL_CATEGORY_CACHE;

    static {
        INITIAL_CATEGORY_CACHE = Arrays.stream(CategoryInfo.values())
                .map(categoryInfo -> new Category(categoryInfo.name(), categoryInfo.url()))
                .collect(Collectors.toList());
    }

    @Id
    @Column(name = "CATEGORY_ID")
    @GeneratedValue
    private Long id;

    private String name;

    private String image;

    @Builder
    private Category(final String name, final String image) {
        this.name = name;
        this.image = image;
    }

    public static List<Category> initialList() {
        return List.copyOf(INITIAL_CATEGORY_CACHE);
    }
}
