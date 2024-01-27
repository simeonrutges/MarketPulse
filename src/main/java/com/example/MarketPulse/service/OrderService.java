package com.example.MarketPulse.service;

import com.example.MarketPulse.dto.OrderDto;
import com.example.MarketPulse.model.Order;
import com.example.MarketPulse.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final DtoMapperService dtoMapperService;


    public OrderService(OrderRepository orderRepository, DtoMapperService dtoMapperService) {
        this.orderRepository = orderRepository;
        this.dtoMapperService = dtoMapperService;
    }

    public List<OrderDto> findAllOrders() {
            List<Order> orders = orderRepository.findAll();
            return orders.stream()
                    .map(dtoMapperService::orderToOrderDto)
                    .collect(Collectors.toList());
        }


    public OrderDto findOrderById(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order != null) {
            OrderDto orderDto = dtoMapperService.orderToOrderDto(order);
        }
        else

        return orderDto;
    }

    public OrderDto createOrder(OrderDto orderDto) {

    }
}
