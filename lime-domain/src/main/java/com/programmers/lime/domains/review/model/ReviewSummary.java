package com.programmers.lime.domains.review.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;

@Builder
public record ReviewSummary(

	Long reviewId,

	int rate,

	String content,

	List<String> imageUrls,

	LocalDateTime createdAt,

	LocalDateTime updatedAt
) {
}
