package com.ray.pominowner.global.config;

import com.ray.pominowner.global.vo.CategoryCache;
import com.ray.pominowner.store.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryCacheLoader implements ApplicationRunner {

    private final CategoryRepository repository;

    private final CategoryCache cache;

    @Override
    public void run(final ApplicationArguments args) {
        log.info("-----------caching initial category list-----------");
        cache.add(repository.findAll());
    }

}
