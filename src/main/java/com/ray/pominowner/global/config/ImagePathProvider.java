package com.ray.pominowner.global.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ImagePathProvider {

    private final String storeImageRootPath;
    private final String storeLogoImageSubPath;

    public ImagePathProvider(@Value("${file.store.image.path}") String storeImageRootPath,
                             @Value("${file.store.logo.path}") String storeLogoImageSubPath) {
        this.storeImageRootPath = storeImageRootPath;
        this.storeLogoImageSubPath = storeLogoImageSubPath;
    }

}
