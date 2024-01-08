package com.programmers.lime.domains.review;

import java.util.List;
import java.util.stream.LongStream;

import com.programmers.lime.domains.review.model.ReviewCursorSummary;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewCursorSummaryBuilder {

	public static ReviewCursorSummary build(final Long reviewId) {
		return ReviewCursorSummary.builder()
			.cursorId("202301010000000000000" + reviewId)
			.reviewId(reviewId)
			.rate(1)
			.content("content")
			.build();
	}

	public static List<ReviewCursorSummary> buildMany() {
		return LongStream.range(1, 11)
			.mapToObj(ReviewCursorSummaryBuilder::build)
			.toList();
	}
}
