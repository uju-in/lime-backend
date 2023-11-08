package com.programmers.bucketback.domains.comment.api.dto.request;

import jakarta.validation.constraints.NotNull;

public record CommentCreateRequest(
	@NotNull
	String content
) {
}
