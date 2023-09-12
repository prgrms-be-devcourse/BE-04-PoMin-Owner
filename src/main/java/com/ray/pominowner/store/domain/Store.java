package com.ray.pominowner.store.domain;

import com.ray.pominowner.global.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseTimeEntity {

    private static final String DEFAULT_VALUE = "NOT_SELECTED";

    @Id
    @Column(name = "STORE_ID")
    @GeneratedValue
    private Long id;

    @Embedded
    private RequiredStoreInfo requiredStoreInfo;

    @Column(columnDefinition = "TEXT default 'NOT_SELECTED'")
    private String info = DEFAULT_VALUE;    // 선택

    @Column(columnDefinition = "TEXT default 'NOT_SELECTED'")
    private String tel = DEFAULT_VALUE;    // 선택

    private Long ownerId;   // 추후 연관관계 설정 예정

    public Store(final RequiredStoreInfo requiredStoreInfo) {
        validateRequiredInfo(requiredStoreInfo);

        this.requiredStoreInfo = requiredStoreInfo;
    }

    private void validateRequiredInfo(final RequiredStoreInfo requiredStoreInfo) {
        Assert.notNull(requiredStoreInfo, "가게 정보는 필수 입니다.");
    }

    public Long getId() {
        return id;
    }

    public Long getBusinessNumber() {
        return requiredStoreInfo.getBusinessNumber();
    }

}
