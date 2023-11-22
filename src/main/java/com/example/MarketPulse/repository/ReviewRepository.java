package com.example.MarketPulse.repository;

import com.example.MarketPulse.model.Review;
import com.example.MarketPulse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
