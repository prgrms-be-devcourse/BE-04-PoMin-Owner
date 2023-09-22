package com.ray.pominowner.global.vo;

import com.ray.pominowner.menu.domain.Menu;
import com.ray.pominowner.menu.service.vo.MenuInfo;
import com.ray.pominowner.store.domain.Store;
import com.ray.pominowner.store.service.vo.StoreInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class InfoSender {

    public void send(Store store) {
        RestTemplate restTemplate = new RestTemplate();
        StoreInfo body = StoreInfo.from(store);

        HttpEntity<StoreInfo> storeInfoHttpEntity = new HttpEntity<>(body);
        ResponseEntity<String> response = restTemplate.exchange("http://52.78.144.166:8080/api/v1/stores", HttpMethod.POST, storeInfoHttpEntity, String.class);
        validateResponse(response);
    }

    public void send(Menu menu) {
        RestTemplate restTemplate = new RestTemplate();
        MenuInfo body = MenuInfo.from(menu);

        HttpEntity<MenuInfo> menuInfoHttpEntity = new HttpEntity<>(body);
        ResponseEntity<String> response = restTemplate.exchange("http://52.78.144.166:8080/api/v1/stores", HttpMethod.POST, menuInfoHttpEntity, String.class);
        validateResponse(response);
    }

    private void validateResponse(ResponseEntity<String> exchange) {
        if (!exchange.getStatusCode().is2xxSuccessful()) {
            throw new IllegalArgumentException("상점 정보를 전송하는데 실패했습니다.");
        }
    }

}
