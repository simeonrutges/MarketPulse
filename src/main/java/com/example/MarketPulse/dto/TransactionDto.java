package com.example.MarketPulse.dto;

import java.util.Date;

public class TransactionDto {
        public Long id;
        public Double amount;
        public Date transactionDate;
        public String status;
        public Long userId; // Id van de gebruiker die de transactie heeft uitgevoerd
        public Long orderId; // Id van de bestelling gekoppeld aan deze transactie

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public Double getAmount() {
                return amount;
        }

        public void setAmount(Double amount) {
                this.amount = amount;
        }

        public Date getTransactionDate() {
                return transactionDate;
        }

        public void setTransactionDate(Date transactionDate) {
                this.transactionDate = transactionDate;
        }

        public String getStatus() {
                return status;
        }

        public void setStatus(String status) {
                this.status = status;
        }

        public Long getUserId() {
                return userId;
        }

        public void setUserId(Long userId) {
                this.userId = userId;
        }

        public Long getOrderId() {
                return orderId;
        }

        public void setOrderId(Long orderId) {
                this.orderId = orderId;
        }
}
