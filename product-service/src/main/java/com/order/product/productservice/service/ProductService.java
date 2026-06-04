package com.order.product.productservice.service;

import com.order.product.productservice.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {

    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    public Product create(Product product) {

        Long id = idGenerator.incrementAndGet();

        Product createdProduct =
                new Product(id, product.getName(), product.getPrice());

        products.put(id, createdProduct);

        return createdProduct;
    }

    public List<Product> getAll() {
        return new ArrayList<>(products.values());
    }

    public Product getById(Long id) {
        return products.get(id);
    }
}