package com.programmers.bucketback.domains.comment.api;

import com.programmers.bucketback.domains.comment.api.dto.request.CommentCreateRequest;
import com.programmers.bucketback.domains.comment.api.dto.request.CommentModifyRequest;
import com.programmers.bucketback.domains.comment.api.dto.response.CommentGetCursorResponse;
import com.programmers.bucketback.domains.comment.application.CommentService;
import com.programmers.bucketback.domains.common.vo.CursorRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "comments", description = "댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feeds/{feedId}/comments")
public class CommentController {

	private final CommentService commentService;

	@Operation(summary = "댓글 생성", description = "FeedId, CommentCreateRequest 을 이용하여 댓글을 생성힙니다.")
	@PostMapping
	public ResponseEntity<Void> createComment(
		@PathVariable final Long feedId,
		@Valid @RequestBody final CommentCreateRequest request
	) {
		commentService.createComment(feedId, request.content());

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "댓글 채택", description = "FeedId, CommentId 을 이용하여 댓글을 채택힙니다.")
	@PostMapping("/{commentId}/adoption")
	public ResponseEntity<Void> adoptComment(
		@PathVariable final Long feedId,
		@PathVariable final Long commentId
	) {
		commentService.adoptComment(feedId, commentId);

		return ResponseEntity.ok().build();
	}

	@PutMapping("/{commentId}")
	public ResponseEntity<Void> modifyComment(
		@PathVariable final Long feedId,
		@PathVariable final Long commentId,
		@Valid @RequestBody final CommentModifyRequest request
	) {
		commentService.modifyComment(feedId, commentId, request.content());

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{commentId}")
	public ResponseEntity<Void> deleteComment(
		@PathVariable final Long feedId,
		@PathVariable final Long commentId
	) {
		commentService.deleteComment(feedId, commentId);

		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<CommentGetCursorResponse> getFeedComments(
		@PathVariable final Long feedId,
		@Valid @ModelAttribute final CursorRequest cursorRequest
	) {
		CommentGetCursorResponse commentGetCursorResponse =
			commentService.getFeedComments(feedId, cursorRequest.toParameters());

		return ResponseEntity.ok(commentGetCursorResponse);
	}
}
