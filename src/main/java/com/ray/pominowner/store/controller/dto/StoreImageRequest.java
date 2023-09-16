package com.ray.pominowner.store.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record StoreImageRequest (@NotEmpty List<MultipartFile> images) {
}
