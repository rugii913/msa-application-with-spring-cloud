package com.example.orderservice.service;

import com.example.orderservice.dto.*;
import com.example.orderservice.jpa.OrderEntity;
import com.example.orderservice.jpa.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderCreationResponseDto createOrder(OrderCreationServiceRequestDto orderCreationServiceRequestDto) {
        OrderEntity orderEntity = orderRepository.save(orderCreationServiceRequestDto.toEntity());
        return OrderCreationResponseDto.from(orderEntity);
    }

    public OrderSearchResponseDto findOrder(String orderId) {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        return OrderSearchResponseDto.from(orderEntity);
    }

    public OrderListResponseDto findOrdersOfUser(String userId) {
        List<OrderEntity> orderList = orderRepository.findByUserId(userId);
        return new OrderListResponseDto(orderList.stream().map(OrderListItemDto::from).toList());
    }
}
