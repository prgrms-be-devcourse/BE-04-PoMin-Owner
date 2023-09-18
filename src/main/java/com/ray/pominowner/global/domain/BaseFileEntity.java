package com.ray.pominowner.global.domain;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseFileEntity extends BaseTimeEntity {

    protected String path;

    protected String uploadName;

    protected String fileName;

    protected void validateImage(String path, String uploadName, String fileName) {
        Assert.hasText(path, "경로는 빈 값일 수 없습니다.");
        Assert.hasText(uploadName, "파일 이름은 빈 값일 수 없습니다.");
        Assert.hasText(fileName, "파일 이름은 빈 값일 수 없습니다.");
    }

}
