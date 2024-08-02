package com.example.hieugiaybe.controller;

import com.example.hieugiaybe.dto.OrderDto;
import com.example.hieugiaybe.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createNewOrder(@RequestBody OrderDto orderDto,@RequestParam("formData") String[] arr ){
       return null;
    }
}
