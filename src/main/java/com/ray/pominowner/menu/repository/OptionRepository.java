package com.ray.pominowner.menu.repository;

import com.ray.pominowner.menu.domain.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {
}
