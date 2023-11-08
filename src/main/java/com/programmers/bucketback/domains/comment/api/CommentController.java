package com.programmers.bucketback.domains.comment.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.bucketback.domains.comment.api.dto.request.CommentCreateRequest;
import com.programmers.bucketback.domains.comment.api.dto.request.CommentModifyRequest;
import com.programmers.bucketback.domains.comment.application.CommentGetCursorResponse;
import com.programmers.bucketback.domains.comment.application.CommentService;
import com.programmers.bucketback.domains.common.vo.CursorRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feeds/{feedId}/comments")
public class CommentController {

	private final CommentService commentService;

	@PostMapping
	public ResponseEntity<Void> createComment(
		@PathVariable final Long feedId,
		@Valid @RequestBody final CommentCreateRequest request
	) {
		commentService.createComment(feedId, request.content());

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

	@GetMapping("")
	public ResponseEntity<CommentGetCursorResponse> getFeedComments(
		@PathVariable final Long feedId,
		@Valid @ModelAttribute("request") final CursorRequest cursorRequest
	) {
		CommentGetCursorResponse commentGetCursorResponse =
			commentService.getFeedComments(feedId, cursorRequest.toParameters());

		return ResponseEntity.ok(commentGetCursorResponse);
	}
}
