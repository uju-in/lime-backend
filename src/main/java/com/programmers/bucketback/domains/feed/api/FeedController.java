package com.programmers.bucketback.domains.feed.api;

import com.programmers.bucketback.domains.common.vo.CursorRequest;
import com.programmers.bucketback.domains.feed.api.request.FeedCreateRequest;
import com.programmers.bucketback.domains.feed.api.request.FeedUpdateRequest;
import com.programmers.bucketback.domains.feed.api.response.FeedCreateResponse;
import com.programmers.bucketback.domains.feed.api.response.FeedGetByCursorResponse;
import com.programmers.bucketback.domains.feed.api.response.FeedGetResponse;
import com.programmers.bucketback.domains.feed.application.FeedService;
import com.programmers.bucketback.domains.feed.application.dto.response.FeedGetByCursorServiceResponse;
import com.programmers.bucketback.domains.feed.application.dto.response.FeedGetServiceResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping
	public ResponseEntity<FeedGetByCursorResponse> getFeedByCursor(
		@RequestParam final String hobbyName,
		@RequestParam(required = false) final String nickname,
		@RequestParam(required = false) final String sortCondition,
		@ModelAttribute @Valid final CursorRequest request
	) {
		FeedGetByCursorServiceResponse serviceResponse = feedService.getFeedByCursor(hobbyName, nickname, sortCondition,
			request.toParameters());

		FeedGetByCursorResponse response = FeedGetByCursorResponse.from(serviceResponse);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{feedId}")
	public ResponseEntity<FeedGetResponse> getFeed(@PathVariable final Long feedId) {
		final FeedGetServiceResponse serviceResponse = feedService.getFeed(feedId);
		final FeedGetResponse response = FeedGetResponse.from(serviceResponse);

		return ResponseEntity.ok(response);
	}
}
