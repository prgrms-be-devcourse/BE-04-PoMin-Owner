package com.ray.pominowner.store.service.adapter;

import com.ray.pominowner.store.domain.Category;
import com.ray.pominowner.store.domain.Store;

import java.util.List;

public interface StoreServiceAdapter {

    List<Category> getCategories();

    void storeCategories(Store store, List<String> categoryRequest);

}
