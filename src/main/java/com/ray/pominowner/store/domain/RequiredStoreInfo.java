package com.ray.pominowner.store.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.regex.Pattern;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequiredStoreInfo {

    private static final String BUSINESS_NUMBER_REGEX = "^[0-9]{10}$";

    @Getter
    private String businessNumber;

    private String name;

    private String mainAddress;

    private String detailAddress;

    private String logoImage;

    public RequiredStoreInfo(String businessNumber,
                             String name,
                             String mainAddress,
                             String detailAddress,
                             String logoImage) {
        validateConstructor(businessNumber, name, mainAddress, detailAddress,logoImage);

        this.businessNumber = businessNumber;
        this.name = name;
        this.mainAddress = mainAddress;
        this.detailAddress = detailAddress;
        this.logoImage = logoImage;
    }

    private void validateConstructor(String businessNumber,
                                     String name,
                                     String mainAddress,
                                     String detailAddress,
                                     String logoImage) {
        Assert.isTrue(Pattern.matches(BUSINESS_NUMBER_REGEX, businessNumber), "사업자등록번호는 숫자 10자리 입니다.");
        Assert.hasText(name, "가게 이름은 필수 입니다.");
        Assert.hasText(mainAddress, "가게 주소는 필수 입니다.");
        Assert.hasText(detailAddress, "가게 상세 주소는 필수 입니다.");
        Assert.hasText(logoImage, "가게 로고 이미지는 필수 입니다.");
    }

}
