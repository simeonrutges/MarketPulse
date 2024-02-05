package com.example.MarketPulse.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class UserDto {
        public Long id;
        @NotBlank(message = "Gebruikersnaam mag niet leeg zijn")
        @Size(min = 3, max = 50, message = "Gebruikersnaam moet tussen 3 en 50 tekens lang zijn")
        public String username;
        @NotBlank(message = "E-mail mag niet leeg zijn")
        @Email(message = "Ongeldig e-mailformaat")
        public String email;
        @NotBlank(message = "Wachtwoord moet tussen de 6 en 30 tekens bevatten")
        @Size(min = 6, max = 30)
        public String password;

        public List<String> roles;
        public List<Long> sellingProductIds; // Id's van de producten die de gebruiker verkoopt
        public List<Long> orderIds; // Id's van de bestellingen van de gebruiker
        public List<Long> reviewIds; // Id's van de reviews van de gebruiker
        public Long cartId;

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
