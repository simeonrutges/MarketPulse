package com.example.MarketPulse.repository;

import com.example.MarketPulse.model.Transaction;
import com.example.MarketPulse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByStatus(String pending);

    List <Transaction> findByUserId(Long userId);

}
