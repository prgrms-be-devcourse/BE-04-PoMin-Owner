package com.ray.pominowner.store.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.pominowner.global.config.TokenProvider;
import com.ray.pominowner.store.domain.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class StoreServiceValidator {

    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper;

//    public void validateBusinessNumber(Long businessNumber) throws JsonProcessingException {  // 추구 사용 예정
//        RestTemplate restTemplate = new RestTemplate();
//        final String businessNumberServiceKey = tokenProvider.getBusinessNumberServiceKey();
//        final String url = "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=" + businessNumberServiceKey + "&returnType=JSON";
//
//        log.info("----------",url);
//        Map<String, List<String>> request = Map.of("b_no", List.of(businessNumber.toString()));
//
//        String msg = objectMapper.writeValueAsString(request);
//        log.info("message = {}-----------",msg);
////        restTemplate.getInterceptors().add((request1, body, execution) -> {
////            request1.getHeaders().setContentType(MediaType.APPLICATION_JSON);
////            return execution.execute(request1, body);
////        });
//        String result = restTemplate.postForObject(url, msg, String.class);
//
//        log.info("string = {}-----------", result);
//
//    }

    public void validateCategory(final List<String> categoryRequest, final List<Category> caches) {
        validateIfRequestCategoryIsNull(categoryRequest);
        validateIfRequestCategoryExists(categoryRequest, caches);
        validateDuplicateCategory(categoryRequest);
    }

    private void validateIfRequestCategoryIsNull(final List<String> categoryRequest) {
        Assert.notEmpty(categoryRequest, "카테고리 리스트에 요소가 하나 이상이어야 합니다.");
    }

    private void validateIfRequestCategoryExists(final List<String> categories, final List<Category> caches) {
        categories.stream()
                .filter(category -> caches.stream().noneMatch(cache -> cache.hasSameName(category)))
                .findAny()
                .ifPresent(category -> {
                    throw new IllegalArgumentException("존재하지 않는 카테고리가 포함되어 있습니다.");
                });
    }

    private void validateDuplicateCategory(final List<String> categoryRequest) {
        List<String> distinctList = categoryRequest.stream()
                .distinct()
                .toList();

        if (distinctList.size() != categoryRequest.size()) {
            throw new IllegalArgumentException("중복된 카테고리가 있습니다.");
        }
    }

}
