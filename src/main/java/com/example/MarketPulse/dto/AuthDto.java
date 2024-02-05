package com.example.MarketPulse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthDto {
//        @NotBlank(message = "Gebruikersnaam mag niet leeg zijn")
//        @Size(min = 4, max = 50, message = "Gebruikersnaam moet tussen 4 en 50 tekens lang zijn")
        public String username;
//        @NotBlank(message = "Wachtwoord mag niet leeg zijn")
//        @Size(min = 8, max = 100, message = "Wachtwoord moet tussen 8 en 100 tekens lang zijn")
        public String password;
}
