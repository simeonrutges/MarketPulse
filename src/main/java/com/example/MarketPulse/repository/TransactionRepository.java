package com.example.MarketPulse.repository;

import com.example.MarketPulse.model.Transaction;
import com.example.MarketPulse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
