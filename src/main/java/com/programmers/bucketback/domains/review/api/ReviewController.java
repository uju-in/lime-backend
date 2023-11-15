package com.programmers.bucketback.domains.review.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.bucketback.domains.common.vo.CursorRequest;
import com.programmers.bucketback.domains.review.api.dto.request.ReviewCreateRequest;
import com.programmers.bucketback.domains.review.api.dto.request.ReviewUpdateRequest;
import com.programmers.bucketback.domains.review.api.dto.response.ReviewCreateResponse;
import com.programmers.bucketback.domains.review.api.dto.response.ReviewGetByCursorResponse;
import com.programmers.bucketback.domains.review.application.ReviewService;
import com.programmers.bucketback.domains.review.application.dto.ReviewCreateServiceResponse;
import com.programmers.bucketback.domains.review.application.dto.ReviewGetByCursorServiceResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items/{itemId}/reviews")
public class ReviewController {

	private final ReviewService reviewService;

	@PostMapping()
	public ResponseEntity<ReviewCreateResponse> createReview(
		@PathVariable final Long itemId,
		@Valid @RequestBody final ReviewCreateRequest request
	) {
		ReviewCreateServiceResponse serviceResponse = reviewService.createReview(itemId, request.toReviewContent());
		ReviewCreateResponse response = ReviewCreateResponse.from(serviceResponse);

		return ResponseEntity.ok(response);
	}

	@PutMapping("/{reviewId}")
	public ResponseEntity<Void> updateReview(
		@PathVariable final Long itemId,
		@PathVariable final Long reviewId,
		@Valid @RequestBody final ReviewUpdateRequest request
	) {
		reviewService.updateReview(itemId, reviewId, request.toReviewContent());

		return ResponseEntity.ok().build();
	}

	@GetMapping()
	public ResponseEntity<ReviewGetByCursorResponse> getReviewsByCursor(
		@PathVariable final Long itemId,
		@ModelAttribute("request") @Valid final CursorRequest request
	) {
		ReviewGetByCursorServiceResponse serviceResponse = reviewService.getReviewsByCursor(
			itemId,
			request.toParameters()
		);

		ReviewGetByCursorResponse response = ReviewGetByCursorResponse.from(serviceResponse);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{reviewId}")
	public ResponseEntity<Void> deleteReview(
		@PathVariable final Long itemId,
		@PathVariable final Long reviewId
	) {
		reviewService.deleteReview(itemId, reviewId);

		return ResponseEntity.ok().build();
	}
}
