package com.ray.pominowner.store.service;

import com.ray.pominowner.global.vo.CategoryCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class StoreServiceValidator {

    private final CategoryCache cache;

    public void validateCategory(final List<String> categories) {
        validateIfRequestCategoryIsNull(categories);
        cache.validateIfCategoryExists(categories);
        validateDuplicateCategory(categories);
    }

    private void validateIfRequestCategoryIsNull(final List<String> categories) {
        Assert.notEmpty(categories, "카테고리 리스트에 요소가 하나 이상이어야 합니다.");
    }

    private void validateDuplicateCategory(final List<String> categories) {
        List<String> distinctList = categories.stream()
                .distinct()
                .toList();

        if (distinctList.size() != categories.size()) {
            throw new IllegalArgumentException("중복된 카테고리가 있습니다.");
        }
    }

}
