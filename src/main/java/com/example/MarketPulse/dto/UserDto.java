package com.example.MarketPulse.dto;

import java.util.List;

public class UserDto {
        public Long id;
        public String username;
        public String email;
        public List<String> roles; // Hier zou je de rollen als strings kunnen opslaan
        public List<Long> sellingProductIds; // Id's van de producten die de gebruiker verkoopt
        public List<Long> orderIds; // Id's van de bestellingen van de gebruiker
        public List<Long> reviewIds; // Id's van de reviews van de gebruiker
        public Long cartId; // Id van de winkelwagen van de gebruiker

}
