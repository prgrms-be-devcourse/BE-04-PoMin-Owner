package com.ray.pominowner.store.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
public class Information {

    private static final String DEFAULT_VALUE = "EMPTY";
    private static final int MIN_INFO_LENGTH = 10;

    @Column(columnDefinition = "TEXT default 'EMPTY'")
    private String info = DEFAULT_VALUE;

    public Information(String info) {
        validateInfo(info);
        this.info = info;
    }

    private void validateInfo(String info) {
        Assert.hasText(info, "가게 정보는 10글자 이상이어야 합니다.");

        if (info.length() < MIN_INFO_LENGTH) {
            throw new IllegalArgumentException("가게 정보는 10글자 이상이어야 합니다.");
        }
    }

    public String getInfo() {
        return info;
    }

}
