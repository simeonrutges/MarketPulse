package com.example.MarketPulse.dto;

public class CartItemDto {
    public Long id;
    public Long cartId; // Id van de winkelwagen waarin dit item zich bevindt
    public Long productId; // Id van het product in dit winkelwagenitem
    public Long orderId; // Id van de bestelling waartoe dit item behoort (optioneel)
    public int quantity;
    public double price;
}
