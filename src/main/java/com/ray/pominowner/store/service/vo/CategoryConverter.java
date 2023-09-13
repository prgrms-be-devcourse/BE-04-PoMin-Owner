package com.ray.pominowner.store.service.vo;

import com.ray.pominowner.global.vo.CategoryCache;
import com.ray.pominowner.store.domain.Category;
import com.ray.pominowner.store.domain.Store;
import com.ray.pominowner.store.domain.StoreCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryConverter {

    private final CategoryCache categoryCache;

    public List<StoreCategory> convertToStoreCategory(final List<String> categoryRequest, final Store store) {
        List<Category> categories = this.convertToEntity(categoryRequest);

        return categories.stream()
                .map(category -> new StoreCategory(store, category))
                .toList();
    }

    public List<Category> convertToEntity(final List<String> categories) {
        return categories.stream()
                .map(category -> categoryCache.getCategories().stream()
                        .filter(cache -> cache.hasSameName(category))
                        .findAny()
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리가 있습니다.")))
                .toList();
    }
}

