package com.ray.pominowner.global.vo;

import com.ray.pominowner.store.domain.Category;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public final class CategoryCache {

    private final Set<Category> cache;

    public CategoryCache() {
        this.cache = new HashSet<>();
    }

    public void add(List<Category> categories) {
        validateBeforeAddToList(categories);

        this.cache.addAll(categories);
    }

    private void validateBeforeAddToList(List<Category> categories) {
        Assert.notNull(categories, "카테고리 리스트는 null일 수 없습니다.");
        Assert.noNullElements(categories, "카테고리 요소는 null일 수 없습니다.");
    }

    public List<Category> getCategories() {
        return List.copyOf(cache);
    }

    public void validateIfCategoryExists(final List<String> categories) {
        categories.stream()
                .filter(category -> this.cache.stream()
                        .noneMatch(cache -> cache.hasSameName(category)))
                .findAny()
                .ifPresent(category -> {
                    throw new IllegalArgumentException("존재하지 않는 카테고리가 포함되어 있습니다.");
                });
    }

}
