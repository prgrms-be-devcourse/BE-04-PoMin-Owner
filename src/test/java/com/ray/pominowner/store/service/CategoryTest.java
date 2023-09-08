package com.ray.pominowner.store.service;

import com.ray.pominowner.store.domain.Category;
import com.ray.pominowner.store.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CategoryTest {

    @Autowired
    CategoryRepository repository;
    
    @Test
    @DisplayName("test1234")
    void test() {

        List<Category> categories = repository.findAll();

        categories.forEach(System.out::println);

    }
    
}
