package com.example.MarketPulse.dto;

import jakarta.validation.constraints.*;

public class ReviewDto {
    public Long id;
    @NotBlank(message = "Commentaar mag niet leeg zijn")
    @Size(min = 1, max = 500, message = "Commentaar moet tussen 1 en 500 tekens lang zijn")
    public String comment;
    @NotNull(message = "Beoordeling mag niet null zijn")
    @Min(value = 1, message = "Beoordeling moet minstens 1 zijn")
    @Max(value = 5, message = "Beoordeling mag niet meer dan 5 zijn")
    public int rating;
    @NotNull(message = "Reviewer ID mag niet null zijn")
    public Long reviewerId;
    @NotNull(message = "Product ID mag niet null zijn")
    public Long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}

