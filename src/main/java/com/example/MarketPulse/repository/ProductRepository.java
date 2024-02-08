package com.example.MarketPulse.repository;

import com.example.MarketPulse.model.Product;
import com.example.MarketPulse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);

    Optional<Product> findByFileName(String fileName);

}
