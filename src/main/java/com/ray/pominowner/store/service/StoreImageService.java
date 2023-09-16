package com.ray.pominowner.store.service;

import com.ray.pominowner.global.config.ImagePathProvider;
import com.ray.pominowner.store.domain.Store;
import com.ray.pominowner.store.domain.StoreImage;
import com.ray.pominowner.store.repository.StoreImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StoreImageService {

    private static final String DOT = ".";

    private final ImagePathProvider imagePathProvider;

    private final StoreImageRepository storeImageRepository;

    public void saveImages(List<MultipartFile> images, Store store) {
        Assert.noNullElements(images, "올바르지 못한 파일입니다.");
        String rootPath = imagePathProvider.getStoreImageRootPath();
        images.forEach(image -> saveEachFile(store, image, rootPath));
    }

    private void saveEachFile(Store store, MultipartFile image, String rootPath) {
        String originalFilename = image.getOriginalFilename();
        validateFileName(originalFilename);

        String createdFileName = createFileName(originalFilename);
        saveImageToPath(image, rootPath + createdFileName);

        storeImageRepository.save(StoreImage.builder()
                .path(rootPath + createdFileName)
                .uploadName(originalFilename)
                .fileName(createdFileName)
                .store(store)
                .build());
    }

    private void validateFileName(String originalFilename) {
        Assert.notNull(originalFilename, "올바르지 못한 파일입니다.");

        if (!originalFilename.contains(DOT)) {
            throw new IllegalArgumentException("올바르지 못한 파일입니다.");
        }
    }

    private void saveImageToPath(MultipartFile image, String path) {
        try {
            image.transferTo(new File(path));
        } catch (IOException e) {
            throw new RuntimeException("파일 저장에 실패했습니다.",e);
        }
    }

    private String createFileName(String originalFilename) {
        String fileExtension = extractFileExtension(originalFilename);
        return UUID.randomUUID()
                .toString()
                .concat(fileExtension);
    }

    private String extractFileExtension(String originalFilename) {
        int dotIndex = originalFilename.lastIndexOf(".");
        return originalFilename.substring(dotIndex);
    }

}
