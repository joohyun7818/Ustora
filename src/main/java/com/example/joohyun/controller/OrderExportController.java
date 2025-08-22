package com.example.joohyun.controller;

import com.example.joohyun.dto.OrderDTO;
import com.example.joohyun.entity.Order;
import com.example.joohyun.repository.OrderRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/export")
public class OrderExportController {

    private final OrderRepository orderRepository;

    public OrderExportController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/orders")
    public List<OrderDTO> exportOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(OrderDTO::fromEntity).collect(Collectors.toList());
    }
}
