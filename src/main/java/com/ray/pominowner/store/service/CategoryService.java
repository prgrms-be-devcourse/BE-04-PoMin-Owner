package com.ray.pominowner.store.service;


import com.ray.pominowner.store.domain.Category;
import com.ray.pominowner.store.repository.CategoryRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository repository;

    @PostConstruct
    @Transactional
    public void afterLoadingApplication() {
        log.info("called afterLoadingApplication method");
        List<Category> initialCategories = Category.initialList();
        repository.saveAll(initialCategories);
    }

}
