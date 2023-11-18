package com.programmers.bucketback.domains.comment.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommentModifyRequest(

	@Schema(description = "댓글 내용", example = "너무 좋은듯?")
	@Size(max = 300, message = "댓글 내용은 최대 300자 입니다.")
	@NotNull(message = "댓글 내용은 필수 값입니다.")
	String content
) {
}
