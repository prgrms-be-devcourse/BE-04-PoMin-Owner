package com.ray.pominowner.global.config.category;

import com.ray.pominowner.store.domain.Category;
import com.ray.pominowner.store.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CategoryApplicationRunnerTest {

    @Autowired
    CategoryRepository repository;

    @Test
    @DisplayName("어플리케이션이 로드 된 후 DB에 카테고리 리스트가 Insert 된다")
    void successDataInsertToDatabaseAfterLoadingApplication() {
        // given
        List<Category> cachedCategories = CategoryCache.cachedCategoryList();

        // when
        List<Category> categories = repository.findAll();

        // then
        assertThat(categories).hasSize(cachedCategories.size());
    }

}
