package com.programmers.bucketback.domains.bucket.api.dto.response;

import java.util.List;

import com.programmers.bucketback.domains.bucket.application.vo.BucketSummary;
import com.programmers.bucketback.domains.common.CursorSummary;

public record BucketGetByCursorResponse(
	String nextCursorId,
	List<BucketSummary> buckets
) {
	public static BucketGetByCursorResponse from(final CursorSummary<BucketSummary> summary) {
		return new BucketGetByCursorResponse(
			summary.nextCursorId(),
			summary.summaries()
		);
	}
}