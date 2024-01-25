package com.programmers.lime.domains.review;

import java.util.List;

import com.programmers.lime.domains.review.model.ReviewSummary;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewSummaryBuilder {

	public static ReviewSummary build(final Long reviewId) {
		return ReviewSummary.builder()
			.rate(reviewId.intValue() % 5 + 1)
			.content("content" + reviewId)
			.reviewId(reviewId)
			.build();
	}

	public static List<ReviewSummary> buildMany(final List<Long> reviewIds) {
		return reviewIds.stream()
			.map(ReviewSummaryBuilder::build)
			.toList();
	}
}
