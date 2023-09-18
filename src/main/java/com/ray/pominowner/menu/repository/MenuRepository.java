package com.ray.pominowner.menu.repository;

import com.ray.pominowner.menu.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {

}
