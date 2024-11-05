package com.example.orderservice.jpa;

import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface OrderRepository  extends ListCrudRepository<OrderEntity, Long> {

    OrderEntity findByOrderId(String orderId);

    List<OrderEntity> findByUserId(String userId);
}
