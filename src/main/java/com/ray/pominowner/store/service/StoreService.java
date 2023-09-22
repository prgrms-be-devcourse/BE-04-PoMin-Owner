package com.ray.pominowner.store.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ray.pominowner.global.vo.InfoSender;
import com.ray.pominowner.store.domain.Store;
import com.ray.pominowner.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StoreService {

    private final StoreServiceValidator storeServiceValidator;

    private final StoreRepository storeRepository;

    private final StoreCategoryService storeCategoryService;

    private final StoreImageService storeImageService;

    private final InfoSender infoSender;

    public Long registerStore(Store store) throws JsonProcessingException {
        storeServiceValidator.validateBusinessNumber(store.getBusinessNumber());
        Long id = storeRepository.save(store).getId();
        infoSender.send(store);

        return id;
    }

    public void registerCategory(List<String> categories, Long storeId) {
        storeServiceValidator.validateCategory(categories);
        Store store = findStore(storeId);
        storeCategoryService.saveCategories(store, categories);

        infoSender.send(store);
    }

    public void registerPhoneNumber(String phoneNumber, Long storeId) {
        Store store = findStore(storeId).retrieveStoreAfterRegisteringPhoneNumber(phoneNumber);
        storeRepository.save(store);

        infoSender.send(store);
    }

    public void deletePhoneNumber(Long storeId) {
        Store store = findStore(storeId).retrieveStoreAfterDeletingPhoneNumber();
        storeRepository.save(store);

        infoSender.send(store);
    }

    public void registerInformation(String information, Long storeId) {
        Store store = findStore(storeId).retrieveStoreAfterRegisteringInfo(information);
        storeRepository.save(store);

        infoSender.send(store);
    }

    public void deleteInformation(Long storeId) {
        Store store = findStore(storeId).retrieveStoreAfterDeletingInfo();
        storeRepository.save(store);

        infoSender.send(store);
    }

    public void saveStoreImages(List<MultipartFile> images, Long storeId) {
        Store store = findStore(storeId);
        storeImageService.saveImages(images, store);

        infoSender.send(store);
    }

    private Store findStore(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 가게입니다."));
    }

}
