package com.programmers.bucketback.domains.bucket.api.dto.response;

import java.util.List;

import com.programmers.bucketback.domains.bucket.application.vo.BucketMemberItemCursorSummary;
import com.programmers.bucketback.domains.bucket.application.vo.BucketMemberItemSummary;

public record BucketGetMemberItemResponse(
	String nextCursorId,
	int summaryCount,
	List<BucketMemberItemSummary> summaries
) {
	public static BucketGetMemberItemResponse from(final BucketMemberItemCursorSummary summary) {
		return new BucketGetMemberItemResponse(
			summary.nextCursorId(),
			summary.summaryCount(),
			summary.summaries()
		);
	}
}
