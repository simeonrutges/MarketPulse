package com.example.MarketPulse.controller;

import com.example.MarketPulse.dto.ReviewDto;
import com.example.MarketPulse.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewDto reviewDto){
        ReviewDto createdReview = reviewService.createReview(reviewDto);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReviewDto>>getAllReviews(){
        List <ReviewDto> allReviews = reviewService.findAllReviews();
        return ResponseEntity.ok(allReviews);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable Long reviewId){
        ReviewDto review = reviewService.findReviewById(reviewId);
        return ResponseEntity.ok(review);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewDto>updateReview(@PathVariable Long reviewId, @RequestBody ReviewDto reviewDto){
        ReviewDto updatedReviewDto = reviewService.updateReview(reviewId,reviewDto);
        return ResponseEntity.ok(updatedReviewDto);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void>deleteReview(@PathVariable Long reviewId){
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<List<ReviewDto>> getReviewsByProduct(@PathVariable Long productId) {
        List<ReviewDto> reviews = reviewService.findReviewsByProductId(productId);
        return ResponseEntity.ok(reviews);
    }
    @GetMapping("/products/{productId}/average-rating")
    public ResponseEntity<Double> getAverageRatingByProduct(@PathVariable Long productId) {
        double averageRating = reviewService.calculateAverageRating(productId);
        return ResponseEntity.ok(averageRating);
    }
}
