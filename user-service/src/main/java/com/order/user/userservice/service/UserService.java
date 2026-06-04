package com.order.user.userservice.service;

import com.order.user.userservice.model.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    public User create(User user) {

        Long id = idGenerator.incrementAndGet();

        User created = new User(id, user.getName());

        users.put(id, created);

        return created;
    }

    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    public User getById(Long id) {
        return users.get(id);
    }
}