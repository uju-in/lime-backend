package com.programmers.lime.domains.review;

import java.util.List;
import java.util.stream.IntStream;

import com.programmers.lime.domains.review.model.ReviewImageInfo;
import com.programmers.lime.domains.review.model.ReviewInfo;
import com.programmers.lime.domains.review.model.ReviewSummary;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewSummaryBuilder {

	public static ReviewSummary build(final ReviewInfo reviewInfo, final List<String> imageUrls) {
		Long reviewId = reviewInfo.reviewId();
		return ReviewSummary.builder()
			.rate(reviewId.intValue() % 5 + 1)
			.content("content" + reviewId)
			.reviewId(reviewId)
			.imageUrls(imageUrls)
			.build();
	}

	public static List<ReviewSummary> buildMany(
		final List<ReviewInfo> reviewInfos,
		final List<ReviewImageInfo> reviewImageInfos
	) {
		return IntStream.range(0, reviewInfos.size())
			.mapToObj(
				idx -> build(
					reviewInfos.get(idx),
					reviewImageInfos.get(idx).imageUrls()
				)
			)
			.toList();
	}
}
