package com.ray.pominowner.global.vo;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@Component
public class FileSaver {

    private static final String DOT = ".";

    public FileInfo retrieveFileInfoAfterSavingFile(MultipartFile image, String rootPath) {
        String originalFilename = image.getOriginalFilename();
        validateFileName(originalFilename);

        String createdFileName = createFileName(originalFilename);
        saveImageToPath(image, rootPath + createdFileName);

        return new FileInfo(originalFilename, createdFileName);
    }

    private void validateFileName(String originalFilename) {
        Assert.notNull(originalFilename, "올바르지 못한 파일입니다.");

        if (!originalFilename.contains(DOT)) {
            throw new IllegalArgumentException("올바르지 못한 파일입니다.");
        }
    }

    private String createFileName(String originalFilename) {
        String fileExtension = extractFileExtension(originalFilename);
        return UUID.randomUUID()
                .toString()
                .concat(fileExtension);
    }

    private String extractFileExtension(String originalFilename) {
        int dotIndex = originalFilename.lastIndexOf(DOT);
        return originalFilename.substring(dotIndex);
    }

    private void saveImageToPath(MultipartFile image, String path) {
        try {
            image.transferTo(Path.of("/home/ubuntu/code/" + path));
        } catch (IOException e) {
            throw new RuntimeException("파일 저장에 실패했습니다.",e);
        }
    }

}
