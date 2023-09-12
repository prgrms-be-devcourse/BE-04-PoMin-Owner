package com.ray.pominowner.store.service.adapter;

import com.ray.pominowner.global.vo.CategoryCache;
import com.ray.pominowner.store.domain.Category;
import com.ray.pominowner.store.domain.Store;
import com.ray.pominowner.store.domain.StoreCategory;
import com.ray.pominowner.store.repository.StoreCategoryRepository;
import com.ray.pominowner.store.service.vo.CategoryConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StoreServiceAdapterImpl implements StoreServiceAdapter {

    private final CategoryCache categoryCache;
    private final CategoryConverter categoryConverter;
    private final StoreCategoryRepository storeCategoryRepository;

    @Override
    public List<Category> getCategories() {
        return categoryCache.getCategoryCache();
    }

    @Override
    public void storeCategories(Store store, List<String> categoryRequest) {
        List<StoreCategory> storeCategories = categoryConverter.convertToStoreCategory(categoryRequest, store);
        storeCategoryRepository.saveAll(storeCategories);
    }
}
