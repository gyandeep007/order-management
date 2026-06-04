package com.order.order.service;

import com.order.order.client.ProductClient;
import com.order.order.client.UserClient;
import com.order.order.dto.CreateOrderRequest;
import com.order.order.dto.ProductResponse;
import com.order.order.dto.UserResponse;
import com.order.order.model.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderService {

    private final UserClient userClient;
    private final ProductClient productClient;

    private final Map<Long, Order> orders = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    public OrderService(UserClient userClient,
                        ProductClient productClient) {

        this.userClient = userClient;
        this.productClient = productClient;
    }

    public Order create(CreateOrderRequest request) {

        UserResponse user =
                userClient.getUser(request.getUserId());

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        ProductResponse product =
                productClient.getProduct(request.getProductId());

        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        Long orderId = idGenerator.incrementAndGet();

        Double totalPrice =
                product.getPrice() * request.getQuantity();

        Order order = new Order(
                orderId,
                request.getUserId(),
                request.getProductId(),
                request.getQuantity(),
                totalPrice
        );

        orders.put(orderId, order);

        return order;
    }

    public List<Order> getAll() {
        return new ArrayList<>(orders.values());
    }

    public Order getById(Long id) {
        return orders.get(id);
    }
}