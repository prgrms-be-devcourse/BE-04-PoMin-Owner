package com.ray.pominowner.store;

import com.ray.pominowner.store.domain.Category;
import com.ray.pominowner.store.domain.RequiredStoreInfo;
import com.ray.pominowner.store.domain.Store;

public final class DataLoader {

    public static Store store() {
        return new Store(requiredStoreInfo());
    }

    public static Category category() {
        return new Category("name", "description");
    }

    public static RequiredStoreInfo requiredStoreInfo() {
        return new RequiredStoreInfo(1234567890L, "name", "address", "logoImage");
    }
}
