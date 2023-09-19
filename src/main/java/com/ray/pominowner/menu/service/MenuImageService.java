package com.ray.pominowner.menu.service;

import com.ray.pominowner.global.config.ImagePathProvider;
import com.ray.pominowner.global.vo.FileInfo;
import com.ray.pominowner.global.vo.FileSaver;
import com.ray.pominowner.menu.domain.MenuImage;
import com.ray.pominowner.menu.repository.MenuImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class MenuImageService {

    private final ImagePathProvider imagePathProvider;

    private final FileSaver fileSaver;

    private final MenuImageRepository menuImageRepository;

    public MenuImage saveImage(MultipartFile image) {
        final String rootPath = imagePathProvider.getMenuImageRootPath();
        FileInfo fileInfo = fileSaver.retrieveFileInfoAfterSavingFile(image, rootPath);

        return menuImageRepository.save(
                MenuImage.builder()
                        .path(rootPath + fileInfo.createdFileName())
                        .uploadName(fileInfo.originalFileName())
                        .fileName(fileInfo.createdFileName())
                        .build()
        );
    }

}
