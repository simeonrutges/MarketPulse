package com.example.MarketPulse.repository;

import com.example.MarketPulse.model.Category;
import com.example.MarketPulse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
