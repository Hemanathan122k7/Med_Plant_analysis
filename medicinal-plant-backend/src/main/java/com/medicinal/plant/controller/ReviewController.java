package com.medicinal.plant.controller;

import com.medicinal.plant.dto.ReviewRequest;
import com.medicinal.plant.dto.ReviewResponse;
import com.medicinal.plant.model.entity.Review;
import com.medicinal.plant.repository.ReviewRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Reviews", description = "Community review management")
@CrossOrigin(origins = "*")
public class ReviewController {

    private final ReviewRepository reviewRepository;

    @GetMapping
    @Operation(summary = "Get all reviews", description = "Retrieve all community reviews ordered by newest first")
    public ResponseEntity<List<ReviewResponse>> getAllReviews() {
        log.info("📋 Fetching all reviews");
        List<Review> reviews = reviewRepository.findAllByOrderByCreatedAtDesc();
        
        List<ReviewResponse> response = reviews.stream()
                .map(review -> {
                    ReviewResponse dto = new ReviewResponse();
                    dto.setId(review.getId());
                    dto.setUserName(review.getUserName());
                    dto.setUserEmail(review.getUserEmail());
                    dto.setRating(review.getRating());
                    dto.setExperience(review.getExperience());
                    dto.setCreatedAt(review.getCreatedAt());
                    dto.setTimeAgo(ReviewResponse.calculateTimeAgo(review.getCreatedAt()));
                    return dto;
                })
                .collect(Collectors.toList());
        
        log.info("✅ Found {} reviews", response.size());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Submit a review", description = "Submit a new community review")
    public ResponseEntity<ReviewResponse> submitReview(@RequestBody ReviewRequest request) {
        log.info("💬 Submitting review from: {}", request.getUserName());
        
        // Validate input
        if (request.getUserName() == null || request.getUserName().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        if (request.getUserEmail() == null || request.getUserEmail().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        if (request.getRating() == null || request.getRating() < 1 || request.getRating() > 5) {
            return ResponseEntity.badRequest().build();
        }
        
        Review review = new Review();
        review.setUserName(request.getUserName().trim());
        review.setUserEmail(request.getUserEmail().trim());
        review.setRating(request.getRating());
        review.setExperience(request.getExperience() != null ? request.getExperience().trim() : "");
        
        Review saved = reviewRepository.save(review);
        
        ReviewResponse response = new ReviewResponse();
        response.setId(saved.getId());
        response.setUserName(saved.getUserName());
        response.setUserEmail(saved.getUserEmail());
        response.setRating(saved.getRating());
        response.setExperience(saved.getExperience());
        response.setCreatedAt(saved.getCreatedAt());
        response.setTimeAgo(ReviewResponse.calculateTimeAgo(saved.getCreatedAt()));
        
        log.info("✅ Review saved with ID: {}", saved.getId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a review", description = "Delete a review by ID (admin only)")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        log.info("🗑️ Deleting review: {}", id);
        
        if (!reviewRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        reviewRepository.deleteById(id);
        log.info("✅ Review deleted: {}", id);
        return ResponseEntity.noContent().build();
    }
}
