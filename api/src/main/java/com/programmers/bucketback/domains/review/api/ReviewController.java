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

import com.programmers.bucketback.common.cursor.CursorRequest;
import com.programmers.bucketback.domains.review.api.dto.request.ReviewCreateRequest;
import com.programmers.bucketback.domains.review.api.dto.request.ReviewUpdateRequest;
import com.programmers.bucketback.domains.review.api.dto.response.ReviewGetByCursorResponse;
import com.programmers.bucketback.domains.review.application.ReviewService;
import com.programmers.bucketback.domains.review.application.dto.ReviewGetByCursorServiceResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "review", description = "리뷰 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items/{itemId}/reviews")
public class ReviewController {

	private final ReviewService reviewService;

	@Operation(summary = "아이템 리뷰 등록", description = "itemId, ReviewCreateRequest을 이용하여 아이템 리뷰를 등록 합니다.")
	@PostMapping()
	public ResponseEntity<Void> createReview(
		@PathVariable final Long itemId,
		@Valid @RequestBody final ReviewCreateRequest request
	) {
		reviewService.createReview(itemId, request.toReviewContent());

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "아이템 리뷰 수정", description = "itemId, reviewId, ReviewUpdateRequest을 이용하여 아이템 리뷰를 수정 합니다.")
	@PutMapping("/{reviewId}")
	public ResponseEntity<Void> updateReview(
		@PathVariable final Long itemId,
		@PathVariable final Long reviewId,
		@Valid @RequestBody final ReviewUpdateRequest request
	) {
		reviewService.updateReview(itemId, reviewId, request.toReviewContent());

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "아이템 리뷰 목록 조회", description = "itemId, CursorRequest 이용하여 아이템 리뷰 목록 조회 합니다.")
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

	@Operation(summary = "아이템 리뷰 삭제", description = "itemId, reviewId을 이용하여 아이템 리뷰를 삭제 합니다.")
	@DeleteMapping("/{reviewId}")
	public ResponseEntity<Void> deleteReview(
		@PathVariable final Long itemId,
		@PathVariable final Long reviewId
	) {
		reviewService.deleteReview(itemId, reviewId);

		return ResponseEntity.ok().build();
	}
}
