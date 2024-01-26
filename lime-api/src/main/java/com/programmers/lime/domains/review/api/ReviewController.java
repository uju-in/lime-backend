package com.programmers.lime.domains.review.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.programmers.lime.domains.review.api.dto.request.ReviewCreateRequest;
import com.programmers.lime.domains.review.api.dto.request.ReviewUpdateRequest;
import com.programmers.lime.domains.review.api.dto.response.ReviewCreateResponse;
import com.programmers.lime.domains.review.api.dto.response.ReviewGetByCursorResponse;
import com.programmers.lime.domains.review.api.dto.response.ReviewGetResponse;
import com.programmers.lime.domains.review.api.dto.response.ReviewModifyResponse;
import com.programmers.lime.domains.review.application.ReviewService;
import com.programmers.lime.domains.review.application.dto.ReviewGetByCursorServiceResponse;
import com.programmers.lime.domains.review.application.dto.ReviewGetServiceResponse;
import com.programmers.lime.global.cursor.CursorRequest;

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
	public ResponseEntity<ReviewCreateResponse> createReview(
		@PathVariable final Long itemId,
		@Valid @ModelAttribute final ReviewCreateRequest request,
		@RequestPart(value = "multipartReviewImages", required = false) final List<MultipartFile> multipartReviewImages
	) {
		reviewService.createReview(itemId, request.toReviewContent(), multipartReviewImages);
		ReviewCreateResponse response = new ReviewCreateResponse(itemId);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "아이템 리뷰 수정", description = "itemId, reviewId, ReviewUpdateRequest을 이용하여 아이템 리뷰를 수정 합니다.")
	@PutMapping("/{reviewId}")
	public ResponseEntity<ReviewModifyResponse> updateReview(
		@PathVariable final Long itemId,
		@PathVariable final Long reviewId,
		@Valid @RequestBody final ReviewUpdateRequest request
	) {
		reviewService.updateReview(itemId, reviewId, request.toReviewContent());
		ReviewModifyResponse response = new ReviewModifyResponse(itemId);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "아이템 리뷰 목록 조회", description = "itemId, CursorRequest 이용하여 아이템 리뷰 목록 조회 합니다.")
	@GetMapping()
	public ResponseEntity<ReviewGetByCursorResponse> getReviewsByCursor(
		@PathVariable final Long itemId,
		@ModelAttribute("request") @Valid final CursorRequest request,
		@RequestParam(required = false) final String reviewSortCondition
	) {
		ReviewGetByCursorServiceResponse serviceResponse = reviewService.getReviewsByCursor(
			itemId,
			request.toParameters(),
			reviewSortCondition
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

	@Operation(summary = "아이템 리뷰 상세조회", description = "reviewId을 이용하여 아이템 리뷰를 상세 조회 합니다.")
	@GetMapping("/{reviewId}")
	public ResponseEntity<ReviewGetResponse> getReview(
		@PathVariable final Long itemId,
		@PathVariable final Long reviewId
	) {
		ReviewGetServiceResponse serviceResponse = reviewService.getReview(itemId, reviewId);
		ReviewGetResponse response = ReviewGetResponse.from(serviceResponse);

		return ResponseEntity.ok(response);
	}
}
