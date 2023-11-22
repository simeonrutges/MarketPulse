package com.example.MarketPulse.repository;

import com.example.MarketPulse.model.Cart;
import com.example.MarketPulse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
