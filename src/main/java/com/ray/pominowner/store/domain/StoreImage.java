package com.ray.pominowner.store.domain;

import com.ray.pominowner.global.domain.BaseFileEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class StoreImage extends BaseFileEntity {

    @Id
    @Column(name = "STORE_IMAGE_ID")
    @GeneratedValue
    private Long id;

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

    public Store getStore() {
        return store;
    }

}
