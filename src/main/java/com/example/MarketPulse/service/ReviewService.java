package com.example.MarketPulse.service;

import com.example.MarketPulse.dto.ReviewDto;
import com.example.MarketPulse.exceptions.ResourceNotFoundException;
import com.example.MarketPulse.model.Product;
import com.example.MarketPulse.model.Review;
import com.example.MarketPulse.model.User;
import com.example.MarketPulse.repository.ProductRepository;
import com.example.MarketPulse.repository.ReviewRepository;
import com.example.MarketPulse.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final  DtoMapperService dtoMapperService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public ReviewService(ReviewRepository reviewRepository, DtoMapperService dtoMapperService, UserRepository userRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.dtoMapperService = dtoMapperService;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public ReviewDto createReview(ReviewDto reviewDto) {
        User reviewer = userRepository.findById(reviewDto.getReviewerId())
                .orElseThrow(() -> new ResourceNotFoundException("Reviewer not found with ID: " + reviewDto.getReviewerId()));
        Product product = productRepository.findById(reviewDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + reviewDto.getProductId()));

        Review review = dtoMapperService.reviewDtoToReview(reviewDto, reviewer, product);
        Review savedReview = reviewRepository.save(review);
        return dtoMapperService.reviewToDto(savedReview);
    }

    public List<ReviewDto> findAllReviews() {
        List<Review>reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(dtoMapperService :: reviewToDto)
                .collect(Collectors.toList());
    }

    public ReviewDto findReviewById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new ResourceNotFoundException("Review not found with ID: " + reviewId));

        return dtoMapperService.reviewToDto(review);
    }

    public ReviewDto updateReview(Long reviewId, ReviewDto reviewDto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with ID: " + reviewId));

        if (reviewDto.getReviewerId() != null) {
            User reviewer = userRepository.findById(reviewDto.getReviewerId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + reviewDto.getReviewerId()));
            review.setReviewer(reviewer);
        }
        if (reviewDto.getProductId() != null) {
            Product product = productRepository.findById(reviewDto.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + reviewDto.getProductId()));
            review.setProduct(product);
        }

        // Update de review met de nieuwe gegevens
        if (reviewDto.getComment() != null) {
            review.setComment(reviewDto.getComment());
        }
        if (reviewDto.getRating() > 0) {
            review.setRating(reviewDto.getRating());
        }

        Review updatedReview = reviewRepository.save(review);

        return dtoMapperService.reviewToDto(updatedReview);
    }

    public void deleteReview(Long reviewId) {
        if (reviewRepository.existsById(reviewId)) {
            reviewRepository.deleteById(reviewId);
        } else {
            throw new ResourceNotFoundException("Review not found with ID: " + reviewId);
        }
    }

    public List<ReviewDto> findReviewsByProductId(Long productId) {
       List<Review> reviews = reviewRepository.findByProductId(productId);
       return reviews.stream()
               .map(dtoMapperService :: reviewToDto)
               .collect(Collectors.toList());
    }

    public double calculateAverageRating(Long productId) {
        return reviewRepository.findAverageRatingsByProductId(productId)
                .orElse(0.0);
    }
}
