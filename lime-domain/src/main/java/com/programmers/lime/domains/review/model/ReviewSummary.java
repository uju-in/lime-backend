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

	Long likeCount,

	LocalDateTime createdAt,

	LocalDateTime updatedAt
) {

	public static ReviewSummary of(
		final ReviewInfo reviewInfo,
		final List<String> imageUrls
	) {
		return ReviewSummary.builder()
			.reviewId(reviewInfo.reviewId())
			.rate(reviewInfo.rate())
			.content(reviewInfo.content())
			.imageUrls(imageUrls)
			.likeCount(reviewInfo.likeCount())
			.createdAt(reviewInfo.createdAt())
			.updatedAt(reviewInfo.updatedAt())
			.build();
	}
}
