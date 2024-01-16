package com.example.MarketPulse.dto;

public class ReviewDto {
    public Long id;
    public String comment; // Of gebruik 'content' om overeen te komen met de entiteit
    public int rating;
    public Long reviewerId; // Id van de gebruiker die de review heeft geschreven
    public Long productId; // Id van het product waarop de review betrekking heeft
}
