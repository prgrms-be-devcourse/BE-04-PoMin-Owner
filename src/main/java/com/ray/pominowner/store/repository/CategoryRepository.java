package com.ray.pominowner.store.repository;

import com.ray.pominowner.store.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
