package com.example.order_service.repository;

import com.example.order_service.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OrderRepository  extends MongoRepository<Order, String> {
    Optional<Order> findByUuid(String uuid);
}
