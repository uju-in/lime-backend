package com.programmers.lime.domains.review;

import java.util.List;

import com.programmers.lime.domains.review.model.ReviewInfo;

public class ReviewInfoBuilder {

	public static ReviewInfo build(final Long reviewId) {
		return ReviewInfo.builder()
			.rate(reviewId.intValue() % 5 + 1)
			.content("content" + reviewId)
			.reviewId(reviewId)
			.isLiked(false)
			.build();
	}

	public static List<ReviewInfo> buildMany(final List<Long> reviewIds) {
		return reviewIds.stream()
			.map(ReviewInfoBuilder::build)
			.toList();
	}
}
