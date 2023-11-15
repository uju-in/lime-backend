package com.programmers.bucketback.domains.comment.api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommentModifyRequest(
	@Size(max = 300, message = "댓글 내용은 최대 300자 입니다.")
	@NotNull(message = "댓글 내용은 필수 값입니다.")
	String content
) {
}
