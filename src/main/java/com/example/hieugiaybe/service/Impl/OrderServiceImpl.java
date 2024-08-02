package com.example.hieugiaybe.service.Impl;

import com.example.hieugiaybe.dto.OrderDto;
import com.example.hieugiaybe.entities.Orders;
import com.example.hieugiaybe.repository.OrderRepository;
import com.example.hieugiaybe.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;


    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderDto createNewOrder(OrderDto orderDto) {
        return null;
    }
}
