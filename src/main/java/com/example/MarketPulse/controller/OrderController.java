package com.example.MarketPulse.controller;

import com.example.MarketPulse.dto.OrderDto;
import com.example.MarketPulse.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {
    private final OrderService orderService;


    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orderDtos = orderService.findAllOrders();
        return ResponseEntity.ok(orderDtos);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId) {
        OrderDto orderDto = orderService.findOrderById(orderId);
        return ResponseEntity.ok(orderDto);
    }

    // 3. Aanmaken van een nieuwe bestelling
//    @PostMapping
//    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
//        OrderDto savedOrderDto = orderService.createOrder(orderDto);
//        return new ResponseEntity<>(savedOrderDto, HttpStatus.CREATED);
//    }
    @PostMapping( "/create/{userId}")
    public ResponseEntity<OrderDto> createOrder(@PathVariable Long userId) {
        OrderDto orderDto = orderService.createOrder(userId);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long orderId, @RequestBody OrderDto orderDto) {
        OrderDto updatedOrderDto = orderService.updateOrder(orderId, orderDto);
        return ResponseEntity.ok(updatedOrderDto);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersByUserId(@PathVariable Long userId) {
        List<OrderDto> orderDtos = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orderDtos);
    }
}
