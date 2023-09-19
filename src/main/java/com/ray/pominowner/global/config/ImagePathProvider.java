package com.ray.pominowner.global.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ImagePathProvider {

    private final String storeImageRootPath;
    private final String storeLogoImageSubPath;
    private final String menuImageRootPath;

    public ImagePathProvider(@Value("${file.store.image.path}") String storeImageRootPath,
                             @Value("${file.store.logo.path}") String storeLogoImageSubPath,
                             @Value("${file.menu.image.path}") String menuImageRootPath) {
        this.storeImageRootPath = storeImageRootPath;
        this.storeLogoImageSubPath = storeLogoImageSubPath;
        this.menuImageRootPath = menuImageRootPath;
    }

}
