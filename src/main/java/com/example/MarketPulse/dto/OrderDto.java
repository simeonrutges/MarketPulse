package com.example.MarketPulse.dto;

import java.util.Date;
import java.util.List;

public class OrderDto {
        public Long id;
        public Long buyerId; // Id van de koper van de bestelling
        public List<Long> cartItemIds; // Id's van de items in de winkelwagen van de bestelling
        public double totalAmount;
        private Date orderDate;
        public String status;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public Long getBuyerId() {
                return buyerId;
        }

        public void setBuyerId(Long buyerId) {
                this.buyerId = buyerId;
        }

        public List<Long> getCartItemIds() {
                return cartItemIds;
        }

        public void setCartItemIds(List<Long> cartItemIds) {
                this.cartItemIds = cartItemIds;
        }

        public double getTotalAmount() {
                return totalAmount;
        }

        public void setTotalAmount(double totalAmount) {
                this.totalAmount = totalAmount;
        }

        public Date getOrderDate() {
                return orderDate;
        }

        public void setOrderDate(Date orderDate) {
                this.orderDate = orderDate;
        }

        public String getStatus() {
                return status;
        }

        public void setStatus(String status) {
                this.status = status;
        }
}
