package com.ray.pominowner.store.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.pominowner.global.config.TokenProvider;
import com.ray.pominowner.global.vo.CategoryCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Component
@RequiredArgsConstructor
public class StoreServiceValidator {

    private final CategoryCache cache;
    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper;

    public void validateCategory(final List<String> categories) {
        validateIfRequestCategoryIsNull(categories);
        cache.validateIfCategoryExists(categories);
        validateDuplicateCategory(categories);
    }

    private void validateIfRequestCategoryIsNull(final List<String> categories) {
        Assert.notEmpty(categories, "카테고리 리스트에 요소가 하나 이상이어야 합니다.");
    }

    private void validateDuplicateCategory(final List<String> categories) {
        List<String> distinctList = categories.stream()
                .distinct()
                .toList();

        if (distinctList.size() != categories.size()) {
            throw new IllegalArgumentException("중복된 카테고리가 있습니다.");
        }
    }

    public void validateBusinessNumber(String businessNumber) throws JsonProcessingException {
        JsonNode responseBodyData = getValidatedResponseBodyData(businessNumber);

        final String businessNumberFieldName = "b_no";
        final String userTaxTypeFieldName = "tax_type";
        String responseBusinessNumber = responseBodyData.get(businessNumberFieldName).asText();
        String taxType = responseBodyData.get(userTaxTypeFieldName).asText();

        log.debug("b_no = {}, tax_type = {}",businessNumberFieldName, userTaxTypeFieldName);
        if (taxType.equals("국세청에 등록되지 않은 사업자등록번호입니다.")) {
            throw new IllegalArgumentException(MessageFormat.format("{0} : 국세청에 등록되지 않은 사업자등록번호입니다.", responseBusinessNumber));
        }
    }

    private JsonNode getValidatedResponseBodyData(String businessNumber) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        final String serviceKey = tokenProvider.getBusinessNumberServiceKey();
        final String headerAuthKey = tokenProvider.getBusinessNumberHeaderAuthKey();
        final String url = MessageFormat.format("https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey={0}", serviceKey);

        HttpEntity<?> httpEntity = setHttpEntityForRequest(businessNumber, headerAuthKey);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        log.debug("status code = {}", responseEntity.getStatusCode());

        validateStatusCode(responseEntity);
        final String dataFieldName = "data";

        return objectMapper.readTree(responseEntity.getBody())
                .get(dataFieldName)
                .get(0);
    }

    private HttpEntity<?> setHttpEntityForRequest(String businessNumber, String headerAuthKey) {
        HttpHeaders headers = setHeader(headerAuthKey);
        Map<String, List<String>> body = Map.of("b_no", List.of(businessNumber));

        return new HttpEntity<>(body, headers);
    }

    private HttpHeaders setHeader(String headerAuthKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        headers.set(AUTHORIZATION, headerAuthKey);

        return headers;
    }

    private void validateStatusCode(ResponseEntity<String> responseEntity) {
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new IllegalArgumentException("사업자등록번호를 확인할 수 없습니다.");
        }
    }

}
