package com.example.MarketPulse.dto;

import java.util.Date;
import java.util.List;

public class OrderDto {
        public Long id;
        public Long buyerId; // Id van de koper van de bestelling
        public List<Long> cartItemIds; // Id's van de items in de winkelwagen van de bestelling
        public double totalAmount;
        public Date orderDate;
        public String status;

    }
