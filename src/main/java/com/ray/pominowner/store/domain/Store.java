package com.ray.pominowner.store.domain;

import com.ray.pominowner.global.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseTimeEntity {

    @Id
    @Column(name = "STORE_ID")
    @GeneratedValue
    private Long id;

    @Embedded
    private RequiredStoreInfo requiredStoreInfo;

    @Embedded
    private Information info = new Information();

    @Embedded
    private PhoneNumber tel = new PhoneNumber();

    @OneToMany(mappedBy = "store", cascade = ALL, orphanRemoval = true)
    private List<StoreImage> images = new ArrayList<>();

    private Long ownerId;   // 추후 연관관계 설정 예정

    @Builder
    private Store(Long id, RequiredStoreInfo requiredStoreInfo, Information info, PhoneNumber tel, List<StoreImage> images, Long ownerId) {
        this.id = id;
        this.requiredStoreInfo = requiredStoreInfo;
        this.info = info;
        this.tel = tel;
        this.images = images;
        this.ownerId = ownerId;
    }

    public Store(RequiredStoreInfo requiredStoreInfo) {
        validateRequiredInfo(requiredStoreInfo);

        this.requiredStoreInfo = requiredStoreInfo;
    }

    private void validateRequiredInfo(RequiredStoreInfo requiredStoreInfo) {
        Assert.notNull(requiredStoreInfo, "가게 정보는 필수 입니다.");
    }

    public String getBusinessNumber() {
        return requiredStoreInfo.getBusinessNumber();
    }

    public Store retrieveStoreAfterRegisteringPhoneNumber(String phoneNumber) {
        return Store.builder()
                .id(this.id)
                .requiredStoreInfo(this.requiredStoreInfo)
                .info(this.info)
                .tel(new PhoneNumber(phoneNumber))
                .images(this.images)
                .ownerId(this.ownerId)
                .build();
    }

    public Store retrieveStoreAfterDeletingPhoneNumber() {
        return Store.builder()
                .id(this.id)
                .requiredStoreInfo(this.requiredStoreInfo)
                .info(this.info)
                .tel(new PhoneNumber())
                .images(this.images)
                .ownerId(this.ownerId)
                .build();
    }

    public Store retrieveStoreAfterRegisteringInfo(String information) {
        return Store.builder()
                .id(this.id)
                .requiredStoreInfo(this.requiredStoreInfo)
                .info(new Information(information))
                .tel(this.tel)
                .images(this.images)
                .ownerId(this.ownerId)
                .build();
    }

    public Store retrieveStoreAfterDeletingInfo() {
        return Store.builder()
                .id(this.id)
                .requiredStoreInfo(this.requiredStoreInfo)
                .info(new Information())
                .tel(this.tel)
                .images(this.images)
                .ownerId(this.ownerId)
                .build();
    }

    public Long getId() {
        return id;
    }

    public PhoneNumber getPhoneNumber() {
        return tel;
    }

    public Information getInformation() {
        return info;
    }

    public List<StoreImage> getImages() {
        return images;
    }

}
