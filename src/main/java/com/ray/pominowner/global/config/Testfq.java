//package com.ray.pominowner.global.config;
//
//import com.ray.pominowner.store.controller.dto.StoreRegisterRequest;
//import com.ray.pominowner.store.domain.Category;
//import jakarta.validation.Valid;
//import org.springframework.http.ResponseEntity;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import java.net.URI;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class Testfq
//        @PostMapping("/info")
//        public ResponseEntity<Void> registerStore(@RequestBody @Valid StoreRegisterRequest request) {
//
//            List<Category> caches = categoryCache.getCategoryCache();
//            List<String> categories = request.categories();
//
//            // validate   // categories에 이상한 값 있으면 예외
//            categories.stream()
//                    .filter(category -> caches.stream().noneMatch(cache -> cache.hasSameName(category))) // cache의 카테고리 이름과 다른것이 통과
//                    .findAny()
//                    .ifPresent(category -> {
//                        throw new IllegalArgumentException("존재하지 않는 카테고리가 있습니다.");
//                    });
//
//            // category -> Category 엔티티로 변환 필요
//            categories.stream()
//                    .map(category -> caches.stream()
//                            .filter(cache -> cache.hasSameName(category))
//                            .findAny()
//                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리가 있습니다.")))
//                    .collect(Collectors.toSet());
//
//
//            Long storeId = storeService.registerStore(request.toEntity(converted));
//            return ResponseEntity.created(URI.create("/api/v1/stores/info/" + storeId)).build();
//        }{
//}
//
//
//@Transactional
//public Long registerStore(Store store) {
////        validateBusinessNumber(store.getBusinessNumber());
//        return storeRepository.save(store).getId();
//        }
//
//public void validateBusinessNumber(Long businessNumber) throws JsonProcessingException {
//        RestTemplate restTemplate = new RestTemplate();
//final String url = "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=Oi7tL9wqUvkSCM6cn7PAcTwbvKh8AjqkVXzX3TTdOqu9NVVfVNIslDIiQz%2BEFvZyoO0MMpeaei%2BDlmEGzBgPIg%3D%3D&returnType=JSON";
//
//        Map<String, List<Long>> request = new HashMap<>();
//        request.put("b_no", List.of(businessNumber));
//
//        String msg = objectMapper.writeValueAsString(request);
//        log.info("message = {}-----------",msg);
//        String string = restTemplate.postForObject(url, request, String.class);
//
//        log.info("string = {}-----------",string);
//
//        }
