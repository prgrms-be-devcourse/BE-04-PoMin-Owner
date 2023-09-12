package com.ray.pominowner.store.service;

import com.ray.pominowner.store.domain.Store;
import com.ray.pominowner.store.domain.StoreCategory;
import com.ray.pominowner.store.repository.StoreCategoryRepository;
import com.ray.pominowner.store.service.vo.CategoryConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StoreCategoryService {

    private final CategoryConverter categoryConverter;
    private final StoreCategoryRepository storeCategoryRepository;

    public void storeCategories(Store store, List<String> categoryRequest) {
        List<StoreCategory> storeCategories = categoryConverter.convertToStoreCategory(categoryRequest, store);
        storeCategoryRepository.saveAll(storeCategories);
    }
}
