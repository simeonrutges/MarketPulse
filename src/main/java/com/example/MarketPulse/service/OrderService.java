package com.example.MarketPulse.service;

import com.example.MarketPulse.dto.OrderDto;
import com.example.MarketPulse.exceptions.ResourceNotFoundException;
import com.example.MarketPulse.model.CartItem;
import com.example.MarketPulse.model.Order;
import com.example.MarketPulse.model.User;
import com.example.MarketPulse.repository.CartItemRepository;
import com.example.MarketPulse.repository.OrderRepository;
import com.example.MarketPulse.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final DtoMapperService dtoMapperService;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;


    public OrderService(OrderRepository orderRepository, DtoMapperService dtoMapperService, UserRepository userRepository, CartItemRepository cartItemRepository) {
        this.orderRepository = orderRepository;
        this.dtoMapperService = dtoMapperService;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public List<OrderDto> findAllOrders() {
            List<Order> orders = orderRepository.findAll();
            return orders.stream()
                    .map(dtoMapperService::orderToOrderDto)
                    .collect(Collectors.toList());
        }


    public OrderDto findOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new ResourceNotFoundException("Order niet gevonden met ID: " + orderId));

        return dtoMapperService.orderToOrderDto(order);
    }

    public OrderDto createOrder(OrderDto orderDto) {
        Order order = dtoMapperService.orderDtoToOrder(orderDto);
        Order savedOrder = orderRepository.save(order);
        return dtoMapperService.orderToOrderDto(savedOrder);
    }

    public OrderDto updateOrder(Long orderId, OrderDto orderDto) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order niet gevonden met ID: " + orderId));

        // Bijwerken van relevante velden van de Order entiteit
        if (orderDto.getBuyerId() != null) {
            User buyer = userRepository.findById(orderDto.getBuyerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Gebruiker niet gevonden met ID: " + orderDto.getBuyerId()));
            order.setBuyer(buyer);
        }

        if (orderDto.getCartItemIds() != null && !orderDto.getCartItemIds().isEmpty()) {
            List<CartItem> cartItems = orderDto.getCartItemIds().stream()
                    .map(id -> cartItemRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("CartItem niet gevonden met ID: " + id)))
                    .collect(Collectors.toList());
            order.setCartItems(cartItems);
        }

        if (orderDto.getTotalAmount() != 0) {
            order.setTotalAmount(orderDto.getTotalAmount());
        }

        if (orderDto.getOrderDate() != null) {
            order.setOrderDate(orderDto.getOrderDate());
        }

        if (orderDto.getStatus() != null && !orderDto.getStatus().isEmpty()) {
            order.setStatus(orderDto.getStatus());
        }


        Order updatedOrder = orderRepository.save(order);
        return dtoMapperService.orderToOrderDto(updatedOrder);
    }

    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    public List<OrderDto> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByBuyerId(userId);

        return orders.stream()
                        .map(dtoMapperService::orderToOrderDto)
                .collect(Collectors.toList());
                             }
}
