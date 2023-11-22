package com.example.MarketPulse.repository;

import com.example.MarketPulse.model.CartItem;
import com.example.MarketPulse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
