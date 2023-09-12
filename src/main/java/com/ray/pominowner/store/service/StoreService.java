package com.ray.pominowner.store.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ray.pominowner.store.domain.Category;
import com.ray.pominowner.store.domain.Store;
import com.ray.pominowner.store.repository.StoreRepository;
import com.ray.pominowner.store.service.adapter.StoreServiceAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreServiceValidator storeServiceValidator;
    private final StoreRepository storeRepository;
    private final StoreServiceAdapter storeServiceAdapter;

    @Transactional
    public Long registerStore(Store store) {
//        storeServiceValidator.validateBusinessNumber(store.getBusinessNumber());
        return storeRepository.save(store).getId();
    }

    @Transactional
    public void registerCategory(final List<String> categoryRequest, Long storeId) {
        List<Category> caches = storeServiceAdapter.getCategories();
        storeServiceValidator.validateCategory(categoryRequest, caches);
        storeServiceAdapter.storeCategories(findStore(storeId), categoryRequest);
    }

    private Store findStore(final Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 가게입니다."));
    }

}
