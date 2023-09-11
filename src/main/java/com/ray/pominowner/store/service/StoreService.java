package com.ray.pominowner.store.service;

import com.ray.pominowner.store.domain.Store;
import com.ray.pominowner.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public Long registerStore(Store store) {
        return storeRepository.save(store).getId();
    }

}
