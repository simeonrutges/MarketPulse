package com.example.MarketPulse.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Date;

public class TransactionDto {
        public Long id;
        @NotNull(message = "Bedrag mag niet null zijn")
        @Positive(message = "Bedrag moet positief zijn")
        public Double amount;
        @NotNull(message = "Transactiedatum mag niet null zijn")
        public Date transactionDate;
        @NotNull
        public String status;
        @NotNull(message = "Gebruikers-ID mag niet null zijn")
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
