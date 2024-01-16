package com.example.MarketPulse.repository;

import com.example.MarketPulse.model.Product;
import com.example.MarketPulse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
