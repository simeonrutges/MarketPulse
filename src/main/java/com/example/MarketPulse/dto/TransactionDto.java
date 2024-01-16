package com.example.MarketPulse.dto;

import java.util.Date;

public class TransactionDto {
        public Long id;
        public Double amount;
        public Date transactionDate;
        public String status;
        public Long userId; // Id van de gebruiker die de transactie heeft uitgevoerd
        public Long orderId; // Id van de bestelling gekoppeld aan deze transactie
}
