package com.example.MarketPulse.repository;

import com.example.MarketPulse.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
