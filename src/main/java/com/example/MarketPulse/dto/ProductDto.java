package com.example.MarketPulse.dto;

public class ProductDto {
    public Long id;
    public String name;
    public String description;
    public double price;
    public Long sellerId; // Id van de verkoper van het product
    public Long categoryId; // Id van de categorie waartoe het product behoort

}
