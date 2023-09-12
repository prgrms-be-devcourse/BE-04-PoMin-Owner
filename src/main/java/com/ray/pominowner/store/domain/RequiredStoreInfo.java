package com.ray.pominowner.store.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequiredStoreInfo {

    @Getter
    private Long businessNumber;

    private String name;

    private String address;

    private String logoImage;

    public RequiredStoreInfo(final Long businessNumber,
                             final String name,
                             final String address,
                             final String logoImage) {
        validateConstructor(businessNumber, name, address, logoImage);

        this.businessNumber = businessNumber;
        this.name = name;
        this.address = address;
        this.logoImage = logoImage;
    }

    private void validateConstructor(final Long businessNumber,
                                     final String name,
                                     final String address,
                                     final String logoImage) {
        Assert.state(businessNumber > 0, "사업자등록번호는 10자리 입니다.");
        Assert.state(String.valueOf(businessNumber).length() == 10, "사업자등록번호는 10자리 입니다.");
        Assert.hasText(name, "가게 이름은 필수 입니다.");
        Assert.hasText(address, "가게 주소는 필수 입니다.");
        Assert.hasText(logoImage, "가게 로고 이미지는 필수 입니다.");
    }

}
