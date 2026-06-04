package com.order.order.client;

import com.order.order.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProductClient {

    private final RestClient restClient;

    @Value("${product.service.url}")
    private String productServiceUrl;

    public ProductClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public ProductResponse getProduct(Long productId) {

        return restClient.get()
                .uri(productServiceUrl+"/products/{id}", productId)
                .retrieve()
                .body(ProductResponse.class);
    }
}