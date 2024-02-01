package com.example.MarketPulse.dto;

public class ReviewDto {
    public Long id;
    public String comment; // Of gebruik 'content' om overeen te komen met de entiteit
    public int rating;
    public Long reviewerId; // Id van de gebruiker die de review heeft geschreven
    public Long productId; // Id van het product waarop de review betrekking heeft

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

