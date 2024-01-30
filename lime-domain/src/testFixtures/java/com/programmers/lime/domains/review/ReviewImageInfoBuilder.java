package com.programmers.lime.domains.review;

import java.util.List;

import com.programmers.lime.domains.review.model.ReviewImageInfo;

import lombok.Builder;

@Builder
public class ReviewImageInfoBuilder {

	public static ReviewImageInfo build(final Long reviewId, final List<String> imageUrls) {
		return ReviewImageInfo.builder()
			.reviewId(reviewId)
			.imageUrls(imageUrls)
			.build();
	}

	public static List<ReviewImageInfo> buildMany(final List<Long> reviewIds) {
		return reviewIds.stream()
			.map(reviewId -> build(reviewId, List.of("image" + reviewId)))
			.toList();
	}
}
