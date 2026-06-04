package com.order.order.client;

import com.order.order.dto.UserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class UserClient {

    private final RestClient restClient;
    @Value("${user.service.url}")
    private String userServiceUrl;

    public UserClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public UserResponse getUser(Long userId) {

        return restClient.get()
                .uri(userServiceUrl+"/users/{id}", userId)
                .retrieve()
                .body(UserResponse.class);
    }
}