package com.programmers.bucketback.domains.bucket.application.vo;

import java.util.List;

import lombok.Builder;

@Builder
public record BucketMemberItemCursorSummary(
	String nextCursorId,
	int summaryCount,
	List<BucketMemberItemSummary> summaries
) {
	public static BucketMemberItemCursorSummary of(
		final String nextCursorId,
		final int summaryCount,
		final List<BucketMemberItemSummary> summaries
	) {
		return BucketMemberItemCursorSummary.builder()
			.nextCursorId(nextCursorId)
			.summaryCount(summaryCount)
			.summaries(summaries)
			.build();
	}

}
