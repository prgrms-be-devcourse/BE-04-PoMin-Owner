package com.ray.pominowner.global.vo;

import com.ray.pominowner.store.domain.Category;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public final class CategoryCache {

    private final List<Category> categoryCache;

    public CategoryCache() {
        this.categoryCache = new ArrayList<>();
    }

    public void add(List<Category> categories) {
        validateBeforeAddToList(categories);

        this.categoryCache.addAll(categories);
    }

    private void validateBeforeAddToList(List<Category> categories) {
        Assert.notNull(categories, "카테고리는 null일 수 없습니다.");

        if (CollectionUtils.contains(categories.iterator(), null)) {
            throw new IllegalArgumentException("카테고리는 null일 수 잆습니다.");
        }
    }

    public List<Category> getCategoryCache() {
        return List.copyOf(categoryCache);
    }

}
