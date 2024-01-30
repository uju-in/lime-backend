package com.programmers.lime.domains.review;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import com.programmers.lime.domains.member.domain.MemberInfoBuilder;
import com.programmers.lime.domains.review.model.ReviewCursorSummary;
import com.programmers.lime.domains.review.model.ReviewSummary;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewCursorSummaryBuilder {

	public static ReviewCursorSummary build(final Long reviewId, final ReviewSummary reviewSummary) {
		return ReviewCursorSummary.builder()
			.cursorId("202301010000000000000" + reviewId)
			.reviewSummary(reviewSummary)
			.memberInfo(MemberInfoBuilder.build(reviewId))
			.isReviewed(false)
			.build();
	}

	public static List<ReviewCursorSummary> buildMany(final List<Long> reviewIds, final List<ReviewSummary> reviewSummaries) {
		return IntStream.range(0, reviewIds.size())
			.mapToObj(
				idx -> build(
					reviewIds.get(idx),
					reviewSummaries.get(idx)
				)
			)
			.toList();
	}
}
