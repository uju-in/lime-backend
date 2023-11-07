package com.programmers.bucketback.domains.comment.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.bucketback.domains.comment.api.dto.request.CommentCreateRequest;
import com.programmers.bucketback.domains.comment.application.CommentService;

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
}
