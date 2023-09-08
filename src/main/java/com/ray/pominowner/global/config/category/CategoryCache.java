package com.ray.pominowner.global.config.category;

import com.ray.pominowner.store.domain.Category;
import com.ray.pominowner.store.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
@Profile("dev")
public final class CategoryCache implements ApplicationRunner {

    private final CategoryRepository repository;

    private static final List<Category> INITIAL_CATEGORY_CACHE = new ArrayList<>();

    @Override
    public void run(final ApplicationArguments args) throws Exception {
        log.info("-----------caching initial category list-----------");
        List<Category> initialCategories = repository.findAll();

        INITIAL_CATEGORY_CACHE.addAll(initialCategories);
    }

    public static List<Category> initialCategories() {
        return List.copyOf(INITIAL_CATEGORY_CACHE);
    }

}
