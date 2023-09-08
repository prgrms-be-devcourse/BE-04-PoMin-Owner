package com.ray.pominowner.global.config.category;

import com.ray.pominowner.store.domain.Category;
import com.ray.pominowner.store.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryApplicationRunner implements ApplicationRunner {

    private final CategoryRepository repository;

    @Override
    public void run(final ApplicationArguments args) throws Exception {
        log.info("-----------inserting initial category list-----------");
        List<Category> initialCategories = CategoryCache.cachedCategoryList();

        repository.saveAll(initialCategories);
    }

}
