package com.ray.pominowner.store.service;

import com.ray.pominowner.store.domain.Store;
import com.ray.pominowner.store.repository.StoreRepository;
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

    private final StoreCategoryService storeCategoryService;

    @Transactional
    public Long registerStore(Store store) {
        return storeRepository.save(store).getId();
    }

    @Transactional
    public void registerCategory(final List<String> categories, Long storeId) {
        storeServiceValidator.validateCategory(categories);
        storeCategoryService.saveCategories(findStore(storeId), categories);
    }

    private Store findStore(final Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 가게입니다."));
    }

}
