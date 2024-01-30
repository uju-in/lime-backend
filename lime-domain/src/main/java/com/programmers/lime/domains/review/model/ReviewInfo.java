package com.programmers.lime.domains.review.model;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record ReviewInfo(

	Long reviewId,

	int rate,

	String content,

	Long likeCount,

	LocalDateTime createdAt,

	LocalDateTime updatedAt
) {
}
