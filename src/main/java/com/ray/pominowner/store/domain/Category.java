package com.ray.pominowner.store.domain;

import com.ray.pominowner.global.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseTimeEntity {

    @Id
    @Column(name = "CATEGORY_ID")
    @GeneratedValue
    private Long id;

    private String name;

    private String image;


    public Category(final String name, final String image) {
        validateConstructor(name, image);
        this.name = name;
        this.image = image;
    }

    private void validateConstructor(final String name, final String image) {
        Assert.hasText(name, "카테고리 이름은 필수 입니다.");
        Assert.hasText(image, "이미지 url 설정은 필수 입니다.");
    }

}
