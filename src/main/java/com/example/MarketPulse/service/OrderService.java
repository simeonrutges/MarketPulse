package com.example.MarketPulse.service;

import com.example.MarketPulse.dto.OrderDto;
import com.example.MarketPulse.exceptions.ResourceNotFoundException;
import com.example.MarketPulse.model.CartItem;
import com.example.MarketPulse.model.Order;
import com.example.MarketPulse.model.User;
import com.example.MarketPulse.repository.CartItemRepository;
import com.example.MarketPulse.repository.OrderRepository;
import com.example.MarketPulse.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
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

//    public OrderDto createOrder(OrderDto orderDto) {
//        Order order = dtoMapperService.orderDtoToOrder(orderDto);
//        Order savedOrder = orderRepository.save(order);
//        return dtoMapperService.orderToOrderDto(savedOrder);
//    }
//@Transactional
//public OrderDto createOrder(Long userId) {
//    // Zoek de gebruiker en zijn/haar winkelwagen
//    User user = userRepository.findById(userId)
//            .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
//
//    // Maak een nieuwe Order
//    Order order = new Order();
//    order.setBuyer(user);
//    order.setOrderDate(new Date());
//    order.setStatus("PENDING");
//    // ... andere order initialisaties ...
//
//    // Haal CartItems op en koppel ze aan de Order
//    List<CartItem> cartItems = cartItemRepository.findByCartUserId(userId);
//    for (CartItem item : cartItems) {
//        item.setOrder(order); // Koppel elk CartItem aan de Order
//        // Optioneel, afhankelijk van JPA cascade-instellingen, kan deze regel overbodig zijn
//        cartItemRepository.save(item);
//    }
//    order.setCartItems(cartItems); // Voeg de CartItems toe aan de Order
//
//    Order savedOrder = orderRepository.save(order);
//    return dtoMapperService.orderToOrderDto(savedOrder);
//}
@Transactional
public OrderDto createOrder(Long userId) {

    User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

    Order order = new Order();
    order.setBuyer(user);
    order.setOrderDate(new Date());
    order.setStatus("PENDING");
    // ... andere order initialisaties ...

    // Haal CartItems op en koppel ze aan de Order
    List<CartItem> cartItems = cartItemRepository.findByCartUserId(userId);
    for (CartItem item : cartItems) {
        item.setOrder(order); // Koppel elk CartItem aan de Order
        // Optioneel, afhankelijk van JPA cascade-instellingen, kan deze regel overbodig zijn
    }
    order.setCartItems(cartItems);
    order.calculateTotalAmount();

    Order savedOrder = orderRepository.save(order);

    return dtoMapperService.orderToOrderDto(savedOrder);
}



    public OrderDto updateOrder(Long orderId, OrderDto orderDto) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order niet gevonden met ID: " + orderId));

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
