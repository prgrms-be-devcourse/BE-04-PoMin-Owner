package com.ray.pominowner.global.config.category;

import com.ray.pominowner.store.domain.Category;
import com.ray.pominowner.store.repository.CategoryRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public final class CategoryCache {

    private final CategoryRepository repository;

    private static final List<Category> INITIAL_CATEGORY_CACHE;

    static {
        INITIAL_CATEGORY_CACHE = Arrays.stream(InitialCategoryInfo.values())
                .map(initialCategoryInfo -> new Category(initialCategoryInfo.name(), initialCategoryInfo.url()))
                .collect(Collectors.toList());
    }

    public static List<Category> cachedCategoryList() {
        return List.copyOf(INITIAL_CATEGORY_CACHE);
    }

    @PostConstruct
    private void saveInitialCategories() {
        repository.saveAll(INITIAL_CATEGORY_CACHE);
    }

}
