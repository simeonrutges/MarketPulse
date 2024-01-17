package com.example.MarketPulse.dto;

import java.util.List;

public class UserDto {
        public Long id;
        public String username;
        public String email;
        public String password;
        public List<String> roles; // Hier zou je de rollen als strings kunnen opslaan
        public List<Long> sellingProductIds; // Id's van de producten die de gebruiker verkoopt
        public List<Long> orderIds; // Id's van de bestellingen van de gebruiker
        public List<Long> reviewIds; // Id's van de reviews van de gebruiker
        public Long cartId; // Id van de winkelwagen van de gebruiker

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public List<String> getRoles() {
                return roles;
        }

        public void setRoles(List<String> roles) {
                this.roles = roles;
        }

        public List<Long> getSellingProductIds() {
                return sellingProductIds;
        }

        public void setSellingProductIds(List<Long> sellingProductIds) {
                this.sellingProductIds = sellingProductIds;
        }

        public List<Long> getOrderIds() {
                return orderIds;
        }

        public void setOrderIds(List<Long> orderIds) {
                this.orderIds = orderIds;
        }

        public List<Long> getReviewIds() {
                return reviewIds;
        }

        public void setReviewIds(List<Long> reviewIds) {
                this.reviewIds = reviewIds;
        }

        public Long getCartId() {
                return cartId;
        }

        public void setCartId(Long cartId) {
                this.cartId = cartId;
        }
}
