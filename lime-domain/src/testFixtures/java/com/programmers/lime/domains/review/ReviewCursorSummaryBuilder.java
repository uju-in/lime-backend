package com.programmers.lime.domains.review;

import java.util.List;
import java.util.stream.LongStream;

import com.programmers.lime.domains.member.domain.MemberInfoBuilder;
import com.programmers.lime.domains.review.model.ReviewCursorSummary;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewCursorSummaryBuilder {

	public static ReviewCursorSummary build(final Long reviewId) {
		return ReviewCursorSummary.builder()
			.cursorId("202301010000000000000" + reviewId)
			.reviewSummary(ReviewSummaryBuilder.build(reviewId))
			.memberInfo(MemberInfoBuilder.build(reviewId))
			.isReviewed(false)
			.build();
	}

	public static List<ReviewCursorSummary> buildMany(final List<Long> reviewIds) {
		return reviewIds.stream()
			.map(ReviewCursorSummaryBuilder::build)
			.toList();
	}
}
