package com.ray.pominowner.store.service;

import com.ray.pominowner.global.config.ImagePathProvider;
import com.ray.pominowner.global.vo.FileInfo;
import com.ray.pominowner.global.vo.FileSaver;
import com.ray.pominowner.store.domain.Store;
import com.ray.pominowner.store.domain.StoreImage;
import com.ray.pominowner.store.repository.StoreImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StoreImageService {

    private final ImagePathProvider imagePathProvider;

    private final StoreImageRepository storeImageRepository;

    private final FileSaver fileSaver;

    public void saveImages(List<MultipartFile> images, Store store) {
        Assert.noNullElements(images, "올바르지 못한 파일입니다.");
        String rootPath = imagePathProvider.getStoreImageRootPath();
        images.forEach(image -> saveEachFile(store, image, rootPath));
    }

    private void saveEachFile(Store store, MultipartFile image, String rootPath) {
        FileInfo fileInfo = fileSaver.retrieveFileInfoAfterSavingFile(image, rootPath);
        final String createdFileName = fileInfo.createdFileName();
        final String originalFilename = fileInfo.originalFileName();

        storeImageRepository.save(StoreImage.builder()
                .path(rootPath + createdFileName)
                .uploadName(originalFilename)
                .fileName(createdFileName)
                .store(store)
                .build());
    }

}
