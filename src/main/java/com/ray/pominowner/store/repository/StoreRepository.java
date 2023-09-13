package com.ray.pominowner.store.repository;

import com.ray.pominowner.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {

}
