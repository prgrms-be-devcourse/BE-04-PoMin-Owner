package com.ray.pominowner.store.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.Assert;

import java.time.LocalTime;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@EqualsAndHashCode()
@NoArgsConstructor(access = PROTECTED)
public class BusinessHour {

    @Column
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime openTime = LocalTime.MAX;

    @Column
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime closeTime = LocalTime.MAX;


    public BusinessHour(LocalTime openTime, LocalTime closeTime) {
        validateConstructor(openTime, closeTime);
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    private void validateConstructor(LocalTime openTime, LocalTime closeTime) {
        Assert.notNull(openTime, "오픈 시간은 필수 입니다.");
        Assert.notNull(closeTime, "마감 시간은 필수 입니다.");

        if (openTime.isAfter(closeTime)) {
            throw new IllegalArgumentException("오픈 시간은 마감 시간보다 빠를 수 없습니다.");
        }
    }

}
