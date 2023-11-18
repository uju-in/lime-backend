package com.programmers.bucketback.domains.feed.api;

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
import org.springframework.web.bind.annotation.RestController;

import com.programmers.bucketback.common.cursor.CursorRequest;
import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.domains.feed.api.request.FeedCreateRequest;
import com.programmers.bucketback.domains.feed.api.request.FeedUpdateRequest;
import com.programmers.bucketback.domains.feed.api.response.FeedCreateResponse;
import com.programmers.bucketback.domains.feed.api.response.FeedGetByCursorResponse;
import com.programmers.bucketback.domains.feed.api.response.FeedGetResponse;
import com.programmers.bucketback.domains.feed.application.FeedService;
import com.programmers.bucketback.domains.feed.application.dto.response.FeedGetServiceResponse;
import com.programmers.bucketback.domains.feed.model.FeedCursorSummaryLike;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feeds")
public class FeedController {

	private final FeedService feedService;

	@Operation(summary = "피드 생성", description = "FeedCreateRequest 을 이용하여 피드를 생성힙니다.")
	@PostMapping
	public ResponseEntity<FeedCreateResponse> createFeed(@RequestBody @Valid final FeedCreateRequest request) {
		Long feedId = feedService.createFeed(request.toServiceRequest());

		return ResponseEntity.ok(new FeedCreateResponse(feedId));
	}

	@Operation(summary = "피드 수정", description = "FeedUpdateRequest 을 이용하여 피드를 수정힙니다.")
	@PutMapping("/{feedId}")
	public ResponseEntity<Void> modifyFeed(
		@PathVariable final Long feedId,
		@RequestBody @Valid final FeedUpdateRequest request
	) {
		feedService.modifyFeed(feedId, request.toServiceRequest());

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "피드 삭제", description = "FeedId를 이용하여 피드를 삭제힙니다.")
	@DeleteMapping("/{feedId}")
	public ResponseEntity<Void> modifyFeed(@PathVariable final Long feedId) {
		feedService.deleteFeed(feedId);

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "피드 좋아요", description = "FeedId를 이용하여 좋아요를 할 수 있습니다.")
	@PostMapping("/{feedId}/like")
	public ResponseEntity<Void> likeFeed(@PathVariable final Long feedId) {
		feedService.likeFeed(feedId);

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "피드 좋아요 취소", description = "FeedId를 이용하여 좋아요한 피드를 취소 할 수 있습니다.")
	@DeleteMapping("/{feedId}/unlike")
	public ResponseEntity<Void> cancelLikedFeed(@PathVariable final Long feedId) {
		feedService.unLikeFeed(feedId);

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "피드 목록 조회", description = "hobbyName, nickname, sortCondition, CursorRequest을 이용하여 피드 목록 조회 합니다.")
	@GetMapping
	public ResponseEntity<FeedGetByCursorResponse> getFeedByCursor(
		@RequestParam final String hobbyName,
		@RequestParam(required = false) final String nickname,
		@RequestParam(required = false) final String sortCondition,
		@ModelAttribute @Valid final CursorRequest request
	) {
		CursorSummary<FeedCursorSummaryLike> cursorSummary = feedService.getFeedByCursor(
			hobbyName,
			nickname,
			sortCondition,
			request.toParameters()
		);
		FeedGetByCursorResponse response = FeedGetByCursorResponse.from(cursorSummary);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "피드 상세 조회", description = "FeedId를 이용하여 피드를 조회합니다.")
	@GetMapping("/{feedId}")
	public ResponseEntity<FeedGetResponse> getFeed(@PathVariable final Long feedId) {
		final FeedGetServiceResponse serviceResponse = feedService.getFeed(feedId);
		final FeedGetResponse response = FeedGetResponse.from(serviceResponse);

		return ResponseEntity.ok(response);
	}
}
