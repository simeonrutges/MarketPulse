package com.example.MarketPulse.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CartItemDto {
    public Long id;
//    @NotNull(message = "Cart ID mag niet null zijn")
    public Long cartId; // Id van de winkelwagen waarin dit item zich bevindt
//    @NotNull(message = "Product ID mag niet null zijn")
    public Long productId; // Id van het product in dit winkelwagenitem
    public Long orderId; // Id van de bestelling waartoe dit item behoort (optioneel)
    @NotNull(message = "Hoeveelheid mag niet null zijn")
    @Min(value = 1, message = "Hoeveelheid moet minstens 1 zijn")
    public int quantity;
    private double pricePerUnit;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }
}
