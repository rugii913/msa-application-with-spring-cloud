package com.example.orderservice.controller;

import com.example.orderservice.dto.*;
import com.example.orderservice.service.OrderService;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    private final Environment env;
    private final OrderService orderService;

    public OrderController(Environment env, OrderService orderService) {
        this.env = env;
        this.orderService = orderService;
    }

    @GetMapping("/health-check")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok(
                String.format("catalog-service works well on port %s", env.getProperty("local.server.port"))
        );
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<OrderCreationResponseDto> createOrder(@PathVariable String userId, @RequestBody OrderCreationRequestDto orderCreationRequestDto) {
        OrderCreationResponseDto createdOrder = orderService.createOrder(OrderCreationServiceRequestDto.of(userId, orderCreationRequestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderSearchResponseDto> findOrder(@PathVariable String orderId) {
        OrderSearchResponseDto foundOrder = orderService.findOrder(orderId);
        return ResponseEntity.ok(foundOrder);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<OrderListResponseDto> findOrdersOfUser(@PathVariable String userId) {
        OrderListResponseDto foundOrders = orderService.findOrdersOfUser(userId);
        return ResponseEntity.ok(foundOrders);
    }
}
