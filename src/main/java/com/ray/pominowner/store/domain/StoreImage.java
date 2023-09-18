package com.ray.pominowner.store.domain;

import com.ray.pominowner.global.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class StoreImage extends BaseTimeEntity {

    @Id
    @Column(name = "STORE_IAMGE_ID")
    @GeneratedValue
    private Long id;

    private String path;

    private String uploadName;

    private String fileName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "STORE_ID")
    private Store store;

    @Builder
    private StoreImage(String path, String uploadName, String fileName, Store store) {
        validateImage(path, uploadName, fileName);
        this.path = path;
        this.uploadName = uploadName;
        this.fileName = fileName;
        this.store = store;
    }

    private void validateImage(String path, String uploadName, String fileName) {
        Assert.hasText(path, "경로는 빈 값일 수 없습니다.");
        Assert.hasText(uploadName, "파일 이름은 빈 값일 수 없습니다.");
        Assert.hasText(fileName, "파일 이름은 빈 값일 수 없습니다.");
    }

    public Store getStore() {
        return store;
    }

}
