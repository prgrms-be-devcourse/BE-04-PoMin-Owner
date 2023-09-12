package com.ray.pominowner.store.service;

import com.ray.pominowner.store.domain.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class StoreServiceValidator {

    public void validateCategory(final List<String> categoryRequest, final List<Category> caches) {
        validateIfRequestCategoryIsNull(categoryRequest);
        validateIfRequestCategoryExists(categoryRequest, caches);
        validateDuplicateCategory(categoryRequest);
    }

    private void validateIfRequestCategoryIsNull(final List<String> categoryRequest) {
        Assert.notEmpty(categoryRequest, "카테고리 리스트에 요소가 하나 이상이어야 합니다.");
    }

    private void validateIfRequestCategoryExists(final List<String> categories, final List<Category> caches) {
        categories.stream()
                .filter(category -> caches.stream().noneMatch(cache -> cache.hasSameName(category)))
                .findAny()
                .ifPresent(category -> {
                    throw new IllegalArgumentException("존재하지 않는 카테고리가 포함되어 있습니다.");
                });
    }

    private void validateDuplicateCategory(final List<String> categoryRequest) {
        List<String> distinctList = categoryRequest.stream()
                .distinct()
                .toList();

        if (distinctList.size() != categoryRequest.size()) {
            throw new IllegalArgumentException("중복된 카테고리가 있습니다.");
        }
    }

}
