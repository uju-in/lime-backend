package com.programmers.bucketback.domains.review.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.bucketback.domains.review.api.dto.request.ReviewCreateRequest;
import com.programmers.bucketback.domains.review.application.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items/{itemId}/reviews")
public class ReviewController {

	private final ReviewService reviewService;

	@PostMapping()
	public ResponseEntity<Void> createReview(
		@PathVariable Long itemId,
		@Valid @RequestBody final ReviewCreateRequest request
	) {
		reviewService.createReview(itemId, request.toReviewContent());

		return ResponseEntity.ok().build();
	}
}
