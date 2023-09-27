package com.ray.pominowner.menu.domain;

import com.ray.pominowner.global.domain.BaseFileEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuImage extends BaseFileEntity {

    @Id
    @Column(name = "MENU_IMAGE_ID")
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "image")
    private Menu menu;

    @Builder
    private MenuImage(String path, String uploadName, String fileName) {
        validateImage(path, uploadName, fileName);

        this.path = path;
        this.uploadName = uploadName;
        this.fileName = fileName;
    }

    public Long getId() {
        return id;
    }

}
